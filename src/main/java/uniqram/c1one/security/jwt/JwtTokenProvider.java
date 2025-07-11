package uniqram.c1one.security.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import uniqram.c1one.auth.dto.JwtToken;
import uniqram.c1one.security.adapter.CustomUserDetails;
import uniqram.c1one.security.jwt.entity.RefreshToken;
import uniqram.c1one.security.jwt.repository.RefreshTokenRepository;
import uniqram.c1one.user.entity.Users;
import uniqram.c1one.user.repository.UserRepository;

import java.security.Key;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Component
public class JwtTokenProvider {
    private final Key key;
    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;

    private static final long ACCESS_TOKEN_EXPIRE_TIME = Duration.ofHours(1).toMillis();      // 1시간
    private static final long REFRESH_TOKEN_EXPIRE_TIME = Duration.ofDays(14).toMillis();    // 14일

    public JwtTokenProvider(@Value("${jwt.secret}") String secretKey, UserRepository userRepository, RefreshTokenRepository refreshTokenRepository) {
        byte[] decode = Decoders.BASE64.decode(secretKey);
        this.key = Keys.hmacShaKeyFor(decode);
        this.userRepository = userRepository;
        this.refreshTokenRepository = refreshTokenRepository;
    }

    @Transactional
    public JwtToken generateToken(Authentication authentication) {
        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));
        LocalDateTime now = LocalDateTime.now();

        LocalDateTime accessTokenExpiration = now.plusSeconds(ACCESS_TOKEN_EXPIRE_TIME / 1000);
        Date accessTokenExpires = Date.from(accessTokenExpiration.atZone(ZoneId.systemDefault()).toInstant());

        LocalDateTime refreshTokenExpiryDateTime = now.plusSeconds(REFRESH_TOKEN_EXPIRE_TIME / 1000);
        Date refreshTokenExpires = Date.from(refreshTokenExpiryDateTime.atZone(ZoneId.systemDefault()).toInstant());

        // 인증 정보 가져오기
        String username = authentication.getName();
        Users user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found with username: " + username));

        // Check if user is blacklisted
        if (user.isBlacklisted()) {
            throw new RuntimeException("사용자가 블랙리스트에 등록되어 있습니다.");
        }

        String accessToken = Jwts.builder()
                .setSubject(authentication.getName())
                .claim("auth", authorities)
                .claim("userId", user.getId())
                .setExpiration(accessTokenExpires)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();

        String refreshToken = Jwts.builder()
                .setSubject(authentication.getName())
                .setIssuedAt(Date.from(now.atZone(ZoneId.systemDefault()).toInstant()))
                .setExpiration(refreshTokenExpires)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();

        // DB에 리프레시 토큰 저장
        saveRefreshToken(user, refreshToken, refreshTokenExpiryDateTime);

        return JwtToken.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    public Authentication getAuthentication(String accessToken) {
        // 토큰 복호화
        Claims claims = parseClaims(accessToken);

        if (claims.get("auth") == null) {
            throw new RuntimeException("권한 정보가 없는 토큰");
        }

        String username = claims.getSubject();

        Users user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("존재하지 않는 사용자"));

        // Check if user is blacklisted
        if (user.isBlacklisted()) {
            throw new RuntimeException("사용자가 블랙리스트에 등록되어 있습니다.");
        }

        UserDetails principal = new CustomUserDetails(user);

        Collection<? extends GrantedAuthority> authorities = Arrays.stream(claims.get("auth").toString().split(","))
                .map(SimpleGrantedAuthority::new)
                .toList();

        return new UsernamePasswordAuthenticationToken(principal, "", authorities);
    }

    // 토큰 정보 검증
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (SecurityException | MalformedJwtException e) {
            log.info("Invalid JWT Token", e);
        } catch (ExpiredJwtException e) {
            log.info("Expired JWT Token", e);
        } catch (UnsupportedJwtException e) {
            log.info("Unsupported JWT Token", e);
        } catch (IllegalArgumentException e) {
            log.info("JWT claims string is empty.", e);
        }
        return false;
    }


    private Claims parseClaims(String accessToken) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(accessToken)
                    .getBody();
        } catch (ExpiredJwtException e) {
            return e.getClaims();
        }
    }

    @Transactional
    public void saveRefreshToken(Users user, String token, LocalDateTime expiryDate) {
        // 토큰 여부 확인
        Optional<RefreshToken> existingToken = refreshTokenRepository.findByUser(user);

        if (existingToken.isPresent()) {
            // Update existing token
            existingToken.get().updateToken(token, expiryDate);
        } else {
            // Create new token
            RefreshToken refreshToken = RefreshToken.builder()
                    .user(user)
                    .token(token)
                    .expiryDate(expiryDate)
                    .build();
            refreshTokenRepository.save(refreshToken);
        }
    }

    @Transactional
    public JwtToken refreshToken(String refreshToken) {
        // valid refresh token
        if (!validateToken(refreshToken)) {
            throw new RuntimeException("Invalid refresh token");
        }

        // Find refresh token in database
        RefreshToken token = refreshTokenRepository.findByToken(refreshToken)
                .orElseThrow(() -> new RuntimeException("Refresh token not found in database"));

        // Check if token is expired
        if (token.isExpired()) {
            refreshTokenRepository.delete(token);
            throw new RuntimeException("Refresh token expired");
        }

        // Get user from token
        Users user = token.getUser();

        // Check if user is blacklisted
        if (user.isBlacklisted()) {
            refreshTokenRepository.delete(token);
            throw new RuntimeException("사용자가 블랙리스트에 등록되어 있습니다.");
        }

        // Create authentication object
        UserDetails userDetails = new CustomUserDetails(user);
        Collection<GrantedAuthority> authorities = Arrays.asList(new SimpleGrantedAuthority(user.getRole().name()));
        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, "", authorities);

        // Generate new tokens
        return generateToken(authentication);
    }

    @Transactional
    public void deleteRefreshTokenByUser(Users user) {
        refreshTokenRepository.deleteByUser(user);
    }

    @Transactional
    public void deleteRefreshTokenByUsername(String username) {
        userRepository.findByUsername(username)
                .ifPresent(this::deleteRefreshTokenByUser);
    }
  
    @Transactional
    public void deleteRefreshTokenByUserDetails(CustomUserDetails userDetails) {
        if (userDetails != null) {
            userRepository.findById(userDetails.getUserId())
                    .ifPresent(this::deleteRefreshTokenByUser);
        }
    }
}
