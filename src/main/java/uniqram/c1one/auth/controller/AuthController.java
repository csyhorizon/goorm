package uniqram.c1one.auth.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import uniqram.c1one.auth.dto.JwtToken;
import uniqram.c1one.auth.dto.SigninRequest;
import uniqram.c1one.auth.dto.SignupRequest;
import uniqram.c1one.auth.service.AuthService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<JwtToken> signin(@Valid @RequestBody SigninRequest request) {
        JwtToken jwt = authService.signin(request);
        return ResponseEntity.ok(jwt);
    }
}