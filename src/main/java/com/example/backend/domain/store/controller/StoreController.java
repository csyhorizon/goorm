package com.example.backend.domain.store.controller;

import com.example.backend.domain.security.adapter.CustomUserDetails;
import com.example.backend.domain.store.dto.StoreCreateRequest;
import com.example.backend.domain.store.dto.StoreResponse;
import com.example.backend.domain.store.service.StoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/stores")
@RequiredArgsConstructor
public class StoreController {
    private final StoreService storeService;

    @PostMapping
    public ResponseEntity<StoreResponse> save(@AuthenticationPrincipal CustomUserDetails user, @RequestBody
    StoreCreateRequest storeCreateRequest) {
        return ResponseEntity.ok(storeService.save(user.getUserId(), storeCreateRequest));
    }

    @GetMapping("/{storeId}")
    public ResponseEntity<StoreResponse> getStoreDetail(@PathVariable Long storeId) {
        return ResponseEntity.ok(storeService.findById(storeId));
    }

    @DeleteMapping("/{storeId}")
    public ResponseEntity<Void> deleteStore(@PathVariable Long storeId) {
        storeService.delete(storeId);
        return ResponseEntity.noContent().build();
    }
}
