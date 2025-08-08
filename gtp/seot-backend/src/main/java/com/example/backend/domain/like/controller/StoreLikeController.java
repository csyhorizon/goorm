package com.example.backend.domain.like.controller;

import com.example.backend.domain.like.dto.StoreLikeRequest;
import com.example.backend.domain.like.dto.StoreLikeResponse;
import com.example.backend.domain.like.service.StoreLikeService;
import com.example.backend.domain.auth.jwt.security.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/StoreLike")
@RequiredArgsConstructor
public class StoreLikeController {
    private final StoreLikeService storeLikeService;
    @PostMapping
    public ResponseEntity<StoreLikeResponse> likeStore(@RequestBody StoreLikeRequest dto, @AuthenticationPrincipal CustomUserDetails user){
        return ResponseEntity.ok(storeLikeService.likeStore(dto.storeId(),user.getUserId()));
    }

}
