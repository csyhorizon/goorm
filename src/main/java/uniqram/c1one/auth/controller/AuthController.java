package uniqram.c1one.auth.controller;

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
import uniqram.c1one.auth.dto.JwtToken;
import uniqram.c1one.auth.dto.SigninRequest;
import uniqram.c1one.auth.dto.SignupRequest;
import uniqram.c1one.auth.exception.AuthErrorCode;
import uniqram.c1one.auth.exception.AuthException;
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
@Slf4j
public class AuthController {

    private final AuthService authService;
    private final JwtTokenProvider jwtTokenProvider;
    private final ActiveUserService activeUserService;

    // 회원가입
    @PostMapping("/join")
    public ResponseEntity<SimpleResponse> signup(@Valid @RequestBody SignupRequest request) {
        authService.signup(request);
        log.info("/api/auth/join 에 요청이 들어옴");
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new SimpleResponse("회원가입 성공"));
    }

    // 로그인
    @PostMapping("/signin")
    public ResponseEntity<LoginResponse> signin(@Valid @RequestBody SigninRequest request,
                                    HttpServletResponse response,
                                    HttpServletRequest httpRequest) {
        
        log.info("/api/auth/signin 에 요청이 들어옴");
        
        try {
            JwtToken jwtToken = authService.signin(request);

            // 쿠키 설정
            Cookie accessTokenCookie = new Cookie("access_token", jwtToken.getAccessToken());
            accessTokenCookie.setHttpOnly(true);
            accessTokenCookie.setPath("/");
            accessTokenCookie.setMaxAge(3600);
            response.addCookie(accessTokenCookie);

            Cookie refreshTokenCookie = new Cookie("refresh_token", jwtToken.getRefreshToken());
            refreshTokenCookie.setHttpOnly(true);
            refreshTokenCookie.setPath("/");
            refreshTokenCookie.setMaxAge(14 * 24 * 3600);
            response.addCookie(refreshTokenCookie);

            // 인증 처리 및 사용자 정보 반환
            Authentication authentication = jwtTokenProvider.getAuthentication(jwtToken.getAccessToken());

            if (authentication.getPrincipal() instanceof CustomUserDetails userDetails) {
                if (userDetails.isBlacklisted()) {
                    throw new AuthException(AuthErrorCode.USER_BLACKLISTED);
                }

                activeUserService.addActiveUser(userDetails, httpRequest.getRemoteAddr());

                UserDto userDto = new UserDto(
                        userDetails.getUserId(),
                        userDetails.getUsername(),
                        userDetails.getRole()
                );

                LoginResponse loginResponse = new LoginResponse(
                        jwtToken.getAccessToken(),
                        jwtToken.getRefreshToken(),
                        "/index",
                        "로그인 성공",
                        userDto
                );

                return ResponseEntity.ok(loginResponse);
            } else {
                throw new AuthException(AuthErrorCode.INVALID_CREDENTIALS);
            }

        } catch (Exception e) {
            throw new AuthException(AuthErrorCode.SIGNIN_FAILED);
        }
    }


    @PostMapping("/refresh")
    public ResponseEntity<?> refreshToken(@CookieValue(name = "refresh_token", required = false) String refreshToken,
                                         HttpServletResponse response) {
        if (refreshToken == null) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(new ErrorResponse("리프레시 토큰이 없습니다."));
        }

        try {
            // 리프레시 토큰을 사용하여 새로운 토큰 발급
            JwtToken jwtToken = jwtTokenProvider.refreshToken(refreshToken);

            // 새로운 토큰을 쿠키에 설정
            Cookie accessTokenCookie = new Cookie("access_token", jwtToken.getAccessToken());
            accessTokenCookie.setHttpOnly(true);
            accessTokenCookie.setPath("/");
            accessTokenCookie.setMaxAge(3600); // 1시간

            Cookie refreshTokenCookie = new Cookie("refresh_token", jwtToken.getRefreshToken());
            refreshTokenCookie.setHttpOnly(true);
            refreshTokenCookie.setPath("/");
            refreshTokenCookie.setMaxAge(14 * 24 * 3600); // 14일

            response.addCookie(accessTokenCookie);
            response.addCookie(refreshTokenCookie);

            Map<String, Object> responseBody = new HashMap<>();
            responseBody.put("accessToken", jwtToken.getAccessToken());
            responseBody.put("message", "토큰 갱신 성공");

            return ResponseEntity.ok(responseBody);
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(new ErrorResponse("토큰 갱신 실패: " + e.getMessage()));
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(@CookieValue(name = "access_token", required = false) Cookie accessTokenCookie,
                                    HttpServletResponse response,
                                    @AuthenticationPrincipal CustomUserDetails userDetails) {
        if (accessTokenCookie != null) {
            System.out.println(">>> accessTokenCookie 존재");
            String accessToken = accessTokenCookie.getValue();

            // 쿠키 삭제
            accessTokenCookie.setMaxAge(0);
            accessTokenCookie.setPath("/");
            response.addCookie(accessTokenCookie);

            // 리프레시 토큰 쿠키도 삭제
            Cookie refreshTokenCookie = new Cookie("refresh_token", null);
            refreshTokenCookie.setMaxAge(0);
            refreshTokenCookie.setPath("/");
            response.addCookie(refreshTokenCookie);

            // 사용자 ID 추출 및 활성 사용자 제거
            try {
                if (userDetails != null) {
                    // 데이터베이스에서 리프레시 토큰 삭제
                    jwtTokenProvider.deleteRefreshTokenByUserDetails(userDetails);
                    // Redis에서 활성 사용자 제거
                    activeUserService.removeActiveUser(userDetails.getUserId());
                    System.out.println(">>> userDetails에서 userId 추출: " + userDetails.getUserId());
                } else {
                    System.out.println(">>> userDetails가 null 입니다. 토큰에서 사용자 정보 추출 시도");
                    // userDetails가 null인 경우 토큰에서 사용자 정보 추출
                    if (accessToken != null && jwtTokenProvider.validateToken(accessToken)) {
                        Authentication authentication = jwtTokenProvider.getAuthentication(accessToken);
                        if (authentication.getPrincipal() instanceof CustomUserDetails extractedUserDetails) {
                            // Redis에서 활성 사용자 제거
                            activeUserService.removeActiveUser(extractedUserDetails.getUserId());
                            // 데이터베이스에서 리프레시 토큰 삭제
                            jwtTokenProvider.deleteRefreshTokenByUserDetails(extractedUserDetails);
                            System.out.println(">>> 토큰에서 userId 추출: " + extractedUserDetails.getUserId());
                        }
                    } else {
                        System.out.println(">>> 토큰이 유효하지 않거나 null입니다.");
                    }
                }
            } catch (Exception e) {
                System.out.println(">>> 로그아웃 처리 중 오류 발생: " + e.getMessage());
                e.printStackTrace();
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
