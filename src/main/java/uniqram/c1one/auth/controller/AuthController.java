package uniqram.c1one.auth.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import uniqram.c1one.auth.dto.JwtToken;
import uniqram.c1one.auth.dto.SigninRequest;
import uniqram.c1one.auth.dto.SignupRequest;
import uniqram.c1one.auth.service.AuthService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uniqram.c1one.security.adapter.CustomUserDetails;
import uniqram.c1one.security.jwt.JwtTokenProvider;
import uniqram.c1one.redis.service.ActiveUserService;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final JwtTokenProvider jwtTokenProvider;
    private final ActiveUserService activeUserService;

    // 회원가입
    @PostMapping("/join")
    public ResponseEntity<String> signup(@Valid @RequestBody SignupRequest request) {
        authService.signup(request);
        return ResponseEntity.status(HttpStatus.CREATED).body("회원가입 성공");
    }

    // 로그인
    @PostMapping("/signin")
    public ResponseEntity<?> signin(@Valid @RequestBody SigninRequest request,
                                    HttpServletResponse response,
                                    HttpServletRequest httpRequest) {
        try {
            JwtToken jwtToken = authService.signin(request);

            // JWT 토큰을 쿠키에 설정
            Cookie accessTokenCookie = new Cookie("access_token", jwtToken.getAccessToken());
            accessTokenCookie.setHttpOnly(true);
            accessTokenCookie.setPath("/");
            accessTokenCookie.setMaxAge(24 * 60 * 60); // 24시간


            Cookie refreshTokenCookie = new Cookie("refresh_token", jwtToken.getRefreshToken());
            refreshTokenCookie.setHttpOnly(true);
            refreshTokenCookie.setPath("/");
            refreshTokenCookie.setMaxAge(7 * 24 * 60 * 60); // 7일

            response.addCookie(accessTokenCookie);
            response.addCookie(refreshTokenCookie);

            // 로그인 성공 시 온라인 사용자 추가
            Authentication authentication = jwtTokenProvider.getAuthentication(jwtToken.getAccessToken());
            if (authentication.getPrincipal() instanceof CustomUserDetails userDetails) {
                activeUserService.addActiveUser(userDetails, httpRequest.getRemoteAddr());
            }

            Map<String, Object> responseBody = new HashMap<>();
            responseBody.put("accessToken", jwtToken.getAccessToken());
            responseBody.put("message", "로그인 성공");
            responseBody.put("redirectUrl", "/index");

            return ResponseEntity.ok(responseBody);

        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(new ErrorResponse("로그인 실패: " + e.getMessage()));
        }


    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(@CookieValue(name = "access_token", required = false) Cookie accessTokenCookie,
                                    HttpServletResponse response,
                                    @AuthenticationPrincipal CustomUserDetails userDetails) {
        if (accessTokenCookie != null) {
            System.out.println(">>> accessTokenCookie 존재");
            // 쿠키 삭제
            accessTokenCookie.setMaxAge(0);
            accessTokenCookie.setPath("/");
            response.addCookie(accessTokenCookie);

            // 리프레시 토큰 쿠키도 삭제
            Cookie refreshTokenCookie = new Cookie("refresh_token", null);
            refreshTokenCookie.setMaxAge(0);
            refreshTokenCookie.setPath("/");
            response.addCookie(refreshTokenCookie);

            // Redis에서 활성 사용자 제거
            if (userDetails != null) {
                System.out.println(">>> userDetails: " + userDetails.getUsername());
                activeUserService.removeActiveUser(userDetails.getUserId());
            }else {
                System.out.println(">>> userDetails가 null 입니다.");
            }
        } else {
            System.out.println(">>> accessTokenCookie가 null 입니다.");
        }
        return ResponseEntity.ok(new SimpleResponse("로그아웃 성공"));
    }


    // 응답용 DTO 클래스들
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
    }

}