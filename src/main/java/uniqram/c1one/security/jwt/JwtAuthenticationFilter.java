package uniqram.c1one.security.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import uniqram.c1one.redis.service.ActiveUserService;
import uniqram.c1one.security.adapter.CustomUserDetails;

import java.io.IOException;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;
    private final ActiveUserService activeUserService;

    @Override
    public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        // Request Header 에서 jwt 토큰 추출
        String token = resolveToken(request);
        validToken(token, request);
        filterChain.doFilter(request, response);
    }

    private void validToken(String token, HttpServletRequest request) {
        // 토큰 유효성 검증
        if (token != null && jwtTokenProvider.validateToken(token)) {
            // 인증 정보 생성 및 저장
            Authentication authentication = jwtTokenProvider.getAuthentication(token);
            SecurityContextHolder.getContext().setAuthentication(authentication);

            // 인증 성공 시 Redis에 접속 정보 등록
            if (authentication.getPrincipal() instanceof CustomUserDetails userDetails) {
                String ip = request.getRemoteAddr();
                activeUserService.addActiveUser(userDetails, ip);
            }
        }
    }

    private String resolveToken(HttpServletRequest request) {
        // 쿠키에서 토큰 확인
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("access_token".equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }

        return null;
    }
}
