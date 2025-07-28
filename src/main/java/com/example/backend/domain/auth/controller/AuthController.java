package com.example.backend.domain.auth.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.backend.domain.auth.dto.JwtToken;
import com.example.backend.domain.auth.dto.SigninRequest;
import com.example.backend.domain.auth.dto.SignupRequest;
import com.example.backend.domain.auth.service.AuthService;
import com.example.backend.domain.auth.jwt.security.CustomUserDetails;
import com.example.backend.domain.auth.jwt.JwtTokenProvider;
import com.example.backend.domain.redis.service.ActiveUserService;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {

    private final AuthService authService;
    private final JwtTokenProvider jwtTokenProvider;
    private final ActiveUserService activeUserService;

    @PostMapping("/join")
    public ResponseEntity<SimpleResponse> signup(@Valid @RequestBody SignupRequest request) {
        authService.signup(request);
        log.info("[회원가입 성공] username='{}'", request.getUsername());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new SimpleResponse("회원가입 성공"));
    }

    @PostMapping("/signin")
    public ResponseEntity<LoginResponse> signin(@Valid @RequestBody SigninRequest request,
                                                HttpServletResponse response,
                                                HttpServletRequest httpRequest) {
        JwtToken jwtToken = authService.signin(request);

        addCookie("access_token", jwtToken.getAccessToken(), 3600, response);
        addCookie("refresh_token", jwtToken.getRefreshToken(), 14 * 24 * 3600, response);

        Authentication authentication = jwtTokenProvider.getAuthentication(jwtToken.getAccessToken());
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        String ip = httpRequest.getRemoteAddr();

        activeUserService.addActiveUser(userDetails, ip);

        log.info("[로그인 성공] username='{}', userId={}, ip='{}'", userDetails.getUsername(), userDetails.getUserId(), ip);

        return ResponseEntity.ok(new LoginResponse(
                jwtToken.getAccessToken(),
                jwtToken.getRefreshToken(),
                "/index",
                "로그인 성공",
                new UserDto(userDetails.getUserId(), userDetails.getUsername(), userDetails.getRole())
        ));
    }

    @PostMapping("/refresh")
    public ResponseEntity<?> refreshToken(@CookieValue(name = "refresh_token", required = false) String refreshToken,
                                          HttpServletResponse response) {
        if (refreshToken == null) {
            log.warn("[토큰 갱신 실패] 리프레시 토큰 없음");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErrorResponse("리프레시 토큰이 없습니다."));
        }

        JwtToken jwtToken = jwtTokenProvider.refreshToken(refreshToken);

        addCookie("access_token", jwtToken.getAccessToken(), 3600, response);
        addCookie("refresh_token", jwtToken.getRefreshToken(), 14 * 24 * 3600, response);

        log.info("[토큰 갱신 성공] 새로운 accessToken 발급 완료");

        return ResponseEntity.ok(Map.of(
                "accessToken", jwtToken.getAccessToken(),
                "message", "토큰 갱신 성공"
        ));
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(@CookieValue(name = "access_token", required = false) Cookie accessTokenCookie,
                                    HttpServletResponse response,
                                    @AuthenticationPrincipal CustomUserDetails userDetails) {
        deleteCookie("access_token", response);
        deleteCookie("refresh_token", response);

        if (userDetails != null) {
            jwtTokenProvider.deleteRefreshTokenByUserDetails(userDetails);
            activeUserService.removeActiveUser(userDetails.getUserId());

            log.info("[로그아웃 완료] userId={}, username='{}'", userDetails.getUserId(), userDetails.getUsername());
        } else {
            log.warn("[로그아웃 실패] 인증된 사용자 정보가 없음 (userDetails == null)");
        }

        return ResponseEntity.ok(new SimpleResponse("로그아웃 성공"));
    }

    private void addCookie(String name, String value, int maxAge, HttpServletResponse response) {
        Cookie cookie = new Cookie(name, value);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        cookie.setMaxAge(maxAge);
        response.addCookie(cookie);
    }

    private void deleteCookie(String name, HttpServletResponse response) {
        Cookie cookie = new Cookie(name, null);
        cookie.setPath("/");
        cookie.setMaxAge(0);
        response.addCookie(cookie);
    }

    @Getter
    @AllArgsConstructor
    static class ErrorResponse {
        private String message;
    }

    @Getter
    @AllArgsConstructor
    static class SimpleResponse {
        private String message;
    }

    @Getter
    @AllArgsConstructor
    static class LoginResponse {
        private String accessToken;
        private String refreshToken;
        private String redirectUrl;
        private String message;
        private UserDto user;
    }

    @Getter
    @AllArgsConstructor
    static class UserDto {
        private Long id;
        private String username;
        private String role;
    }
}
