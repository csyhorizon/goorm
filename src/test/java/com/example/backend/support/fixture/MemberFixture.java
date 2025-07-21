package com.example.backend.support.fixture;

import com.example.backend.domain.member.entity.Role;
import com.example.backend.domain.member.entity.Member;
import lombok.Getter;

@Getter
public enum MemberFixture {
    회원("test@naver.com", "testtest", "김회원", Role.OWNER),
    회투("test2@naver.com", "testtest22", "김회투", Role.USER);

    private String email;
    private String password;
    private String name;
    private Role role;

    MemberFixture(String email, String password, String name, Role role) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.role = role;
    }

    public static Member 김회원() {
        return new Member(회원.name, 회원.email, 회원.password, 회원.role);
    }

    public static Member 김회투() {
        return new Member(회투.name, 회투.email, 회투.password, 회투.role);
    }
}
