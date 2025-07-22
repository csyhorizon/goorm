package com.example.backend.domain.member.controller;

import com.example.backend.domain.member.dto.MemberResponse;
import com.example.backend.domain.member.service.MemberService;
import com.example.backend.domain.security.adapter.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/Member")
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;
    @GetMapping
    public ResponseEntity<MemberResponse> getMember(@AuthenticationPrincipal CustomUserDetails user){
        return ResponseEntity.ok(memberService.getMember(user.getUserId()));
    }
}

