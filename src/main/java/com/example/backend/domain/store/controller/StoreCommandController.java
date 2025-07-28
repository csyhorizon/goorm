package com.example.backend.domain.store.controller;

import com.example.backend.domain.auth.jwt.security.CustomUserDetails;
import com.example.backend.domain.store.dto.StoreCreateRequest;
import com.example.backend.domain.store.dto.StoreResponse;
import com.example.backend.domain.store.service.command.StoreCommandService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/stores")
@RequiredArgsConstructor
public class StoreCommandController {
    private final StoreCommandService storeCommandService;

    @Operation(summary = "가게 등록", description = "사장님이 가게를 등록합니다.")
    @ApiResponse(responseCode = "200", description = "가게 등록 성공")
    @PostMapping
    public ResponseEntity<StoreResponse> save(@AuthenticationPrincipal CustomUserDetails user, @RequestBody
    StoreCreateRequest storeCreateRequest) {
        return ResponseEntity.ok(storeCommandService.save(user.getUserId(), storeCreateRequest));
    }

    @Operation(summary = "가게 삭제", description = "사장님이 가게를 삭제합니다.")
    @ApiResponse(responseCode = "200", description = "가게 삭제 성공")
    @DeleteMapping("/{storeId}")
    public ResponseEntity<Void> deleteStore(@PathVariable Long storeId) {
        storeCommandService.delete(storeId);
        return ResponseEntity.noContent().build();
    }
}
