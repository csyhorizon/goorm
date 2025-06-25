package uniqram.c1one.auth.jwt;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import uniqram.c1one.auth.dto.JwtToken;
import uniqram.c1one.security.jwt.JwtTokenProvider;

import java.util.Base64;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;

class JwtTokenProviderTest {

    private JwtTokenProvider provider;

    @BeforeEach
    void setUp() {
        byte[] keyBytes = new byte[32];
        new java.security.SecureRandom().nextBytes(keyBytes);
        String base64Secret = Base64.getEncoder().encodeToString(keyBytes);

        provider = new JwtTokenProvider(base64Secret);
    }

    @Test
    void generate_and_validate_token() {
        // given: 인증 객체
        String username = "testuser";
        SimpleGrantedAuthority role = new SimpleGrantedAuthority("ROLE_USER");
        Authentication auth = new UsernamePasswordAuthenticationToken(
                username, null, Collections.singleton(role)
        );

        // when: 토큰 생성
        JwtToken jwt = provider.generateToken(auth);

        // then: 토큰 잘 생성됐는지
        assertThat(jwt).isNotNull();
        assertThat(jwt.getGrantType()).isEqualTo("Bearer");
        assertThat(jwt.getAccessToken()).isNotBlank();
        assertThat(jwt.getRefreshToken()).isNotBlank();

        // validateToken()도 true
        assertThat(provider.validateToken(jwt.getAccessToken())).isTrue();

        // getAuthentication() 으로 원래 username 복원 가능
        Authentication parsed = provider.getAuthentication(jwt.getAccessToken());
        assertThat(parsed.getName()).isEqualTo(username);

    }

    @Test
    void validate_invalid_token_returns_false() {
        assertThat(provider.validateToken("this.is.not.a.token")).isFalse();
    }

}