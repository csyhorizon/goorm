package uniqram.c1one.auth.jwt;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import uniqram.c1one.auth.dto.JwtToken;
import uniqram.c1one.security.adapter.CustomUserDetails;
import uniqram.c1one.security.jwt.JwtTokenProvider;
import uniqram.c1one.security.jwt.repository.RefreshTokenRepository;
import uniqram.c1one.user.entity.Role;
import uniqram.c1one.user.entity.Users;
import uniqram.c1one.user.repository.UserRepository;

import java.util.Base64;
import java.util.Collections;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

class JwtTokenProviderTest {

    private JwtTokenProvider provider;

    @Mock
    private UserRepository userRepository;

    @Mock
    private RefreshTokenRepository refreshTokenRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        byte[] keyBytes = new byte[32];
        new java.security.SecureRandom().nextBytes(keyBytes);
        String base64Secret = Base64.getEncoder().encodeToString(keyBytes);

        provider = new JwtTokenProvider(base64Secret, userRepository, refreshTokenRepository);
    }

    @Test
    void generate_and_validate_token() {
        // given: 인증 객체
        String username = "testuser";
        SimpleGrantedAuthority role = new SimpleGrantedAuthority("ROLE_USER");
        Authentication auth = new UsernamePasswordAuthenticationToken(
                username, null, Collections.singleton(role)
        );

        Users user = Users.builder()
                .id(3L)
                .username(username)
                .password("dummy")
                .role(Role.USER)
                .build();

        // userRepository mock이 username에 대해 위 유저 리턴하도록 설정
        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));

        // when: 토큰 생성
        JwtToken jwt = provider.generateToken(auth);

        // then: 토큰 잘 생성됐는지
        assertThat(jwt).isNotNull();
        assertThat(jwt.getAccessToken()).isNotBlank();
        assertThat(jwt.getRefreshToken()).isNotBlank();

        // validateToken()도 true
        assertThat(provider.validateToken(jwt.getAccessToken())).isTrue();

        // getAuthentication() 으로 원래 username 복원 가능
        Authentication parsed = provider.getAuthentication(jwt.getAccessToken());
        assertThat(parsed.getName()).isEqualTo(username);

    }

    @Test
    void jwt_인증_후_CustomUserDetails_userId_정상() {
        // given
        String username = "testuser";
        Long userId = 42L;

        // 1. Users 엔티티 (id 포함) 생성
        Users user = Users.builder()
                .id(userId)
                .username(username)
                .password("encodedPassword")
                .role(Role.USER)
                .build();

        // 2. userRepository mock 세팅
        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));

        // 3. 인증 정보 생성 및 토큰 발급
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                username, null, Collections.singleton(new SimpleGrantedAuthority("ROLE_USER"))
        );
        JwtToken jwt = provider.generateToken(authentication);

        // 4. JWT 토큰에서 Authentication 복원
        Authentication authResult = provider.getAuthentication(jwt.getAccessToken());

        // 5. Principal을 CustomUserDetails로 변환
        CustomUserDetails customUserDetails =
                (CustomUserDetails) authResult.getPrincipal();

        // then: userId가 DB의 id와 같은지 검증!
        assertThat(customUserDetails.getUserId()).isEqualTo(userId);
    }

    @Test
    void validate_invalid_token_returns_false() {
        assertThat(provider.validateToken("this.is.not.a.token")).isFalse();
    }

}