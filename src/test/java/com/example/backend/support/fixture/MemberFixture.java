package com.example.backend.support.fixture;

import com.example.backend.domain.member.entity.Role;
import com.example.backend.domain.member.entity.member;
import lombok.Getter;

@Getter
public enum MemberFixture {
    회원("test@naver.com", "testtest", "김회원", Role.USER);
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

    public static member 김회원() {
        return new member(회원.name, 회원.email, 회원.password, 회원.role);
    }
}
