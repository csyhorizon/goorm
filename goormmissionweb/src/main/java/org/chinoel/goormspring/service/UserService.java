package org.chinoel.goormspring.service;

import jakarta.transaction.Transactional;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.chinoel.goormspring.dto.request.SignUpRequest;
import org.chinoel.goormspring.entity.Role;
import org.chinoel.goormspring.entity.User;
import org.chinoel.goormspring.entity.UserRole;
import org.chinoel.goormspring.exception.EmailAlreadyExistsException;
import org.chinoel.goormspring.exception.UsernameAlreadyExistsException;
import org.chinoel.goormspring.repository.RoleRepository;
import org.chinoel.goormspring.repository.UserRepository;
import org.chinoel.goormspring.repository.UserRoleRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final UserRoleRepository userRoleRepository;

    public User findExistUser(Long id) {
        Optional<User> user = userRepository.findById(id);
        return user.orElse(null);
    }

    public User findUserByID(String username) {
        Optional<User> user = userRepository.findByUsername(username);
        return user.orElse(null);
    }

    public void signup(SignUpRequest request) {
        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            throw new UsernameAlreadyExistsException("이미 존재하는 사용자입니다.");
        }
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new EmailAlreadyExistsException("이미 사용중인 이메일입니다.");
        }

        // User Entity
        User user = User.createUser(request, passwordEncoder);
        userRepository.save(user);

        Role defaultRole = roleRepository.findById(2L)
                .orElseThrow(() -> new RuntimeException("생성할 수 없는 권한"));

        // User Role
        UserRole userRole = UserRole.createUserRole(user, defaultRole);
        userRoleRepository.save(userRole);
    }
}
