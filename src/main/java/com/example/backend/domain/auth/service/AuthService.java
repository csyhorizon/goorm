package com.example.backend.domain.auth.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.example.backend.domain.auth.dto.JwtToken;
import com.example.backend.domain.auth.dto.SigninRequest;
import com.example.backend.domain.auth.dto.SignupRequest;
import com.example.backend.domain.auth.exception.AuthErrorCode;
import com.example.backend.domain.auth.exception.AuthException;
import com.example.backend.domain.security.jwt.JwtTokenProvider;
import com.example.backend.domain.user.entity.Role;
import com.example.backend.domain.user.entity.Users;
import com.example.backend.domain.user.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;

    public void signup(SignupRequest request) {
        if (!request.getPassword().equals(request.getConfirmPassword())) {
            throw new AuthException(AuthErrorCode.PASSWORD_MISMATCH);
        }

        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            throw new AuthException(AuthErrorCode.USERNAME_ALREADY_EXISTS);
        }

        String encodedPassword = passwordEncoder.encode(request.getPassword());

        Users user = Users.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(encodedPassword)
                .role(Role.USER)
                .build();

        userRepository.save(user);
    }

    public JwtToken signin(SigninRequest request) {
        // username / password 검증
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(auth);
        return jwtTokenProvider.generateToken(auth);
    }
}
