package uniqram.c1one.auth.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import uniqram.c1one.auth.dto.JwtToken;
import uniqram.c1one.auth.dto.SigninRequest;
import uniqram.c1one.auth.dto.SignupRequest;
import uniqram.c1one.auth.service.AuthService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uniqram.c1one.test.TestAuthController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    // 회원가입
    @PostMapping("/join")
    public ResponseEntity<String> signup(@Valid @RequestBody SignupRequest request) {
        authService.signup(request);
        return ResponseEntity.status(HttpStatus.CREATED).body("회원가입 성공");
    }

    // 로그인
    @PostMapping("/signin")
    public ResponseEntity<?> signin(@Valid @RequestBody SigninRequest request,
                                    HttpServletResponse response) {
        try {
            JwtToken jwtToken = authService.signin(request);

            // JWT 토큰을 쿠키에 설정
            Cookie accessTokenCookie = new Cookie("access_token", jwtToken.getAccessToken());
            accessTokenCookie.setHttpOnly(true);
            accessTokenCookie.setPath("/");

            Cookie refreshTokenCookie = new Cookie("refresh_token", jwtToken.getRefreshToken());
            refreshTokenCookie.setHttpOnly(true);
            refreshTokenCookie.setPath("/");

            response.addCookie(accessTokenCookie);
            response.addCookie(refreshTokenCookie);

            return ResponseEntity.ok(jwtToken);

        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(new ErrorResponse("로그인 실패: " + e.getMessage()));
        }
    }

    @GetMapping("/check")
    public ResponseEntity<?> checkLoginStatus(@CookieValue(name = "access_token", required = false) String accessToken) {
        if (accessToken != null) {
            return ResponseEntity.ok().body(new SimpleResponse("인증된 사용자입니다."));
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new SimpleResponse("인증되지 않은 사용자입니다."));
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
}