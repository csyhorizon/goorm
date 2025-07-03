package uniqram.c1one.admin.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uniqram.c1one.auth.dto.SignupRequest;
import uniqram.c1one.auth.service.AuthService;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor

public class AdminAuthController {

    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<String> signupAdmin(@Valid @RequestBody SignupRequest request) {
        authService.signupAdmin(request);
        return ResponseEntity.status(HttpStatus.CREATED).body("관리자 계정 생성 완료");
    }
}
