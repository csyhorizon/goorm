package com.example.backend.domain.item.controller;

import com.example.backend.domain.item.dto.ItemResponse;
import com.example.backend.domain.item.service.ItemService;
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

    @GetMapping("/{itemId}")
    public ResponseEntity<ItemResponse> getItemDetail(@PathVariable Long itemId) {
        return ResponseEntity.ok(itemService.findById(itemId));
    }
}
