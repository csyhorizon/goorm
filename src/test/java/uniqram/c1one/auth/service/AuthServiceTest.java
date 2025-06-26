package uniqram.c1one.auth.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import uniqram.c1one.auth.dto.SignupRequest;
import uniqram.c1one.global.exception.BaseException;
import uniqram.c1one.user.entity.Role;
import uniqram.c1one.user.entity.Users;
import uniqram.c1one.user.repository.UserRepository;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
class AuthServiceTest {

    @Autowired
    private AuthService authService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    void clearDatabase() {
        userRepository.deleteAll();
    }

    @Test
    @DisplayName("회원가입 성공")
    void signup_success() {
        // given
        SignupRequest request = SignupRequest.builder()
                .username("newuser")
                .password("securepass")
                .confirmPassword("securepass")
                .email("user@example.com")
                .build();

        // when
        authService.signup(request);

        // then
        Users user = userRepository.findByUsername("newuser").orElseThrow();
        assertThat(user.getUsername()).isEqualTo("newuser");
        assertThat(passwordEncoder.matches("securepass", user.getPassword())).isTrue();
    }

    @Test
    @DisplayName("비밀번호 불일치 예외")
    void signup_passwordMismatch_throwsException() {
        // given
        SignupRequest request = SignupRequest.builder()
                .username("user")
                .password("pass1")
                .confirmPassword("pass2")
                .email("user@example.com")
                .build();

        // when & then
        assertThatThrownBy(() -> authService.signup(request))
                .isInstanceOf(BaseException.class)
                .hasMessageContaining("비밀번호가 일치하지 않습니다.");
    }

    @Test
    @DisplayName("중복 아이디 예외")
    void signup_duplicateUsername_throwsException() {
        // given
        userRepository.save(new Users("testUser", "encoded", "test@example.com", Role.USER));

        SignupRequest request = SignupRequest.builder()
                .username("testUser")
                .password("password")
                .confirmPassword("password")
                .email("test@example.com")
                .build();

        // when & then
        assertThatThrownBy(() -> authService.signup(request))
                .isInstanceOf(BaseException.class)
                .hasMessageContaining("이미 존재하는 사용자입니다.");
    }
}