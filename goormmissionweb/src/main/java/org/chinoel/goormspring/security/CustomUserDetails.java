package org.chinoel.goormspring.security;

import java.io.Serializable;
import java.util.Collection;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


// UserDetails : 사용자 인증 정보를 담는 인터페이스
// Serializable : 객체를 직렬화할 수 있게 하는 인터페이스

@Data
public class CustomUserDetails implements UserDetails, Serializable {

    private static final long serialVersionUID = 174726374856727L;

    private Long seq;
    private String loginId;
    private String password;
    private Collection<? extends GrantedAuthority> authorities;


    // 사용자의 권한 목록 반환
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    // 사용자 비밀번호 반환
    @Override
    public String getPassword() {
        return this.password;
    }

    // 사용자 아이디 반환
    @Override
    public String getUsername() {
        return this.loginId;
    }

    // 계정 만료 여부 (true : 만료되지 않음)
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    // 계정 잠금 여부 (true : 잠기지 않음)
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    // 인증 정보 만료 여부 (true : 만료되지 않음)
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    // 계정 활성화 여부 (true : 활성화 됨)
    @Override
    public boolean isEnabled() {
        return true;
    }
}
