package uniqram.c1one.auth.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uniqram.c1one.auth.dto.JwtToken;
import uniqram.c1one.auth.dto.SigninRequest;
import uniqram.c1one.auth.dto.SignupRequest;
import uniqram.c1one.auth.exception.AuthErrorCode;
import uniqram.c1one.auth.exception.AuthException;
import uniqram.c1one.security.jwt.JwtTokenProvider;
import uniqram.c1one.user.entity.Role;
import uniqram.c1one.user.entity.Users;
import uniqram.c1one.user.repository.UserRepository;

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

        Role role = Role.USER;

        Users user = Users.builder()
                .username(request.getUsername())
                .password(encodedPassword)
                .role(role)
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
        // 인증 정보 SecurityContextHolder 에 저장
        SecurityContextHolder.getContext().setAuthentication(auth);

        // JWT 토큰 발급
        return jwtTokenProvider.generateToken(auth);

    }


    }
