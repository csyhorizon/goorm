package com.example.backend.domain.item.controller;

import com.example.backend.domain.item.dto.ItemResponse;
import com.example.backend.domain.item.service.ItemService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/items")
public class ItemController {
    private final ItemService itemService;

    @Operation(summary = "판매 상품 상세 조회", description = "상품을 상세조회 합니다")
    @ApiResponse(responseCode = "200", description = "상품 상세 조회 성공")
    @GetMapping("/{itemId}")
    public ResponseEntity<ItemResponse> getItemDetail(@PathVariable Long itemId) {
        return ResponseEntity.ok(itemService.findById(itemId));
    }
}
