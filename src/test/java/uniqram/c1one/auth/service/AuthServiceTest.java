package uniqram.c1one.auth.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import uniqram.c1one.auth.dto.SignupRequest;
import uniqram.c1one.auth.exception.AuthException;
import uniqram.c1one.user.entity.Role;
import uniqram.c1one.user.entity.Users;
import uniqram.c1one.user.repository.UserRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @InjectMocks
    private AuthService authService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Test
    @DisplayName("회원가입 성공")
    void signupSuccess() {
        // given
        SignupRequest request = new SignupRequest("user01", "pw1234", "pw1234");

        when(userRepository.findByUsername("user01")).thenReturn(Optional.empty());
        when(passwordEncoder.encode("pw1234")).thenReturn("encoded_pw");

        // when
        authService.signup(request);

        // then
        verify(userRepository, times(1)).save(any(Users.class));
    }

    @Test
    @DisplayName("비밀번호 불일치 시 예외 발생")
    void signupPasswordMismatch() {
        // given
        SignupRequest request = new SignupRequest("user01", "pw1234", "different_pw");

        // when & then
        AuthException exception = assertThrows(AuthException.class, () -> authService.signup(request));
        assertEquals("비밀번호가 일치하지 않습니다.", exception.getErrorCode().getMessage());
    }

    @Test
    @DisplayName("username 중복 시 예외 발생")
    void signupDuplicateUsername() {
        // given
        SignupRequest request = new SignupRequest("user01", "pw1234", "pw1234");

        when(userRepository.findByUsername("user01"))
                .thenReturn(Optional.of(new Users("user01", "encoded_pw", Role.USER)));

        // when & then
        AuthException exception = assertThrows(AuthException.class, () -> authService.signup(request));
        assertEquals("이미 존재하는 사용자입니다.", exception.getErrorCode().getMessage());
    }

    @Test
    @DisplayName("관리자 회원가입 성공 - Role.ADMIN 확인")
    void signupAdminSuccess() {
        // given
        SignupRequest request = new SignupRequest("admin", "pw12345", "pw12345");

        when(userRepository.findByUsername("admin")).thenReturn(Optional.empty());
        when(passwordEncoder.encode("pw12345")).thenReturn("encoded_pw");

        ArgumentCaptor<Users> captor = ArgumentCaptor.forClass(Users.class);

        // when
        authService.signupAdmin(request);

        // then
        verify(userRepository).save(captor.capture());
        Users savedUser = captor.getValue();

        assertEquals("admin", savedUser.getUsername());
        assertEquals("encoded_pw", savedUser.getPassword());
        assertEquals(Role.ADMIN, savedUser.getRole());
    }
    @Test
    @DisplayName("관리자 회원가입 실패 - 비밀번호 불일치")
    void signupAdminPasswordMismatch() {
        // given
        SignupRequest request = new SignupRequest("admin", "pw12345", "different_pw");

        // when & then
        AuthException exception = assertThrows(AuthException.class, () -> authService.signupAdmin(request));
        assertEquals("비밀번호가 일치하지 않습니다.", exception.getErrorCode().getMessage());
    }

    @Test
    @DisplayName("관리자 회원가입 실패 - 중복 username")
    void signupAdminDuplicateUsername() {
        // given
        SignupRequest request = new SignupRequest("admin", "pw12345", "pw12345");

        when(userRepository.findByUsername("admin"))
                .thenReturn(Optional.of(new Users("admin", "encoded_pw", Role.ADMIN)));

        // when & then
        AuthException exception = assertThrows(AuthException.class, () -> authService.signupAdmin(request));
        assertEquals("이미 존재하는 사용자입니다.", exception.getErrorCode().getMessage());
    }
}
