package uniqram.c1one.auth.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uniqram.c1one.auth.dto.SignupRequest;
import uniqram.c1one.auth.exception.AuthErrorCode;
import uniqram.c1one.auth.exception.AuthException;
import uniqram.c1one.user.entity.Role;
import uniqram.c1one.user.entity.Users;
import uniqram.c1one.user.repository.UserRepository;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void signup(SignupRequest request) {
        if (!request.getPassword().equals(request.getConfirmPassword())) {
            throw new AuthException(AuthErrorCode.PASSWORD_MISMATCH);
        }

        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            throw new AuthException(AuthErrorCode.USERNAME_ALREADY_EXISTS);
        }

        String encodedPassword = passwordEncoder.encode(request.getPassword());

        Role role = request.getRole() != null ? request.getRole() : Role.USER;

        Users user = new Users(
                request.getUsername(),
                encodedPassword,
                request.getEmail(),
                role
        );

        userRepository.save(user);
    }
}
