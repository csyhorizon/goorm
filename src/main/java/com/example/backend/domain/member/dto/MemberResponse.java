package com.example.backend.domain.member.dto;

import com.example.backend.domain.member.entity.Member;
import com.example.backend.domain.member.entity.Role;
import jakarta.persistence.*;
import lombok.Builder;

public record MemberResponse(
         Long id,
         String username,
         String email,
         Role role
) {

    public static MemberResponse from(Member member){
        return new MemberResponse(
                member.getId(),
                member.getUsername(),
                member.getEmail(),
                member.getRole());
    }
}
