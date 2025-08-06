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
import com.example.backend.domain.auth.jwt.JwtTokenProvider;
import com.example.backend.domain.member.entity.Role;
import com.example.backend.domain.member.entity.Member;
import com.example.backend.domain.member.repository.MemberRepository;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final MemberRepository userRepository;
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

        Member user = Member.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(encodedPassword)
                .role(request.getRole())
                .build();

        userRepository.save(user);
    }

    public void signupAdmin(SignupRequest request) {
        if (!request.getPassword().equals(request.getConfirmPassword())) {
            throw new AuthException(AuthErrorCode.PASSWORD_MISMATCH);
        }

        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            throw new AuthException(AuthErrorCode.USERNAME_ALREADY_EXISTS);
        }

        String encodedPassword = passwordEncoder.encode(request.getPassword());

        Member user = Member.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(encodedPassword)
                .role(Role.ADMIN)
                .build();

        userRepository.save(user);
    }

    public JwtToken signin(SigninRequest request) {
        // email / password 검증
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(auth);
        return jwtTokenProvider.generateToken(auth);
    }
}
