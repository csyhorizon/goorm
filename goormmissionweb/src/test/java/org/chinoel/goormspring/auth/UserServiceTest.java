package org.chinoel.goormspring.auth;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.UUID;
import org.chinoel.goormspring.dto.request.SignUpRequest;
import org.chinoel.goormspring.entity.User;
import org.chinoel.goormspring.repository.UserRepository;
import org.chinoel.goormspring.service.UserService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @AfterEach
    @DisplayName("test 강제 계정 생성")
    void testUser() {
        try {
            // given
            SignUpRequest request = new SignUpRequest();
            request.setUsername("test");
            request.setPassword("test");
            request.setEmail("test@naver.com");
            request.setNickname("nickName");

            // when
            userService.signup(request);
        } catch (IllegalArgumentException e) {}
    }

    @Test
    @DisplayName("USER DB 전체 드랍 방지")
    void DBdeleteAll() {
        assertThrows(UnsupportedOperationException.class, () -> {
            userRepository.deleteAll();
        });
    }

    @Test
    @DisplayName("이미 존재하는 사용자")
    void 회원가입_실패() {
        assertThrows(IllegalArgumentException.class, () -> {
            // given
            SignUpRequest request = new SignUpRequest();
            request.setUsername("test");
            request.setPassword("test");
            request.setEmail("test@naver.com");
            request.setNickname("nickName");

            // when
            userService.signup(request);
        });
    }

    @Test
    @DisplayName("회원가입 테스트")
    void 회원가입_성공() {
        String userName = String.valueOf(UUID.randomUUID());

        // given
        SignUpRequest request = new SignUpRequest();
        request.setUsername(userName);
        request.setPassword("test");
        request.setEmail(userName + "@naver.com");
        request.setNickname("nickName");

        // when
        userService.signup(request);

        // then
        User user = userRepository.findByUsername(userName)
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));

        assertThat(user.getUsername()).isEqualTo(userName);
        assertThat(passwordEncoder.matches("test", user.getPassword())).isTrue();
        assertThat(user.getEmail()).isEqualTo(userName + "@naver.com");
        assertThat(user.getNickname()).isEqualTo("nickName");
    }
}