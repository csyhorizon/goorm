package com.example.backend.domain.store.controller;

import com.example.backend.domain.event.dto.EventCreateRequest;
import com.example.backend.domain.event.dto.EventResponse;
import com.example.backend.domain.event.service.EventService;
import com.example.backend.domain.item.dto.ItemCreateRequest;
import com.example.backend.domain.item.dto.ItemResponse;
import com.example.backend.domain.item.service.ItemService;
import com.example.backend.domain.security.adapter.CustomUserDetails;
import com.example.backend.domain.store.dto.StoreCreateRequest;
import com.example.backend.domain.store.dto.StoreResponse;
import com.example.backend.domain.store.service.StoreService;
import java.util.List;
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
    private final ItemService itemService;
    private final EventService eventService;

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

    @PostMapping("/{storeId}/items")
    public ResponseEntity<ItemResponse> createStoreItem(@AuthenticationPrincipal CustomUserDetails user,
                                                        @PathVariable Long storeId,
                                                        @RequestBody ItemCreateRequest itemCreateRequest) {
        return ResponseEntity.ok(itemService.save(user.getUserId(), storeId, itemCreateRequest));
    }

    @GetMapping("/{storeId}/items")
    public ResponseEntity<List<ItemResponse>> getStoreItems(@PathVariable Long storeId) {
        return ResponseEntity.ok(itemService.findAllByStoreId(storeId));
    }

    @DeleteMapping("/{storeId}/{itemId}")
    public ResponseEntity<Void> deleteItem(@AuthenticationPrincipal CustomUserDetails user,
                                           @PathVariable Long storeId,
                                           @PathVariable Long itemId) {
        itemService.deleteItem(user.getUserId(), storeId, itemId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{storeId}/events")
    public ResponseEntity<EventResponse> createEvents(@AuthenticationPrincipal CustomUserDetails user,
                                                      @PathVariable Long storeId,
                                                      @RequestBody EventCreateRequest eventCreateRequest) {
        return ResponseEntity.ok(eventService.save(user.getUserId(), storeId, eventCreateRequest));
    }

    @GetMapping("/{storeId}/events")
    public ResponseEntity<List<EventResponse>> getEvents(@PathVariable Long storeId) {
        return ResponseEntity.ok(eventService.getAllEvents(storeId));
    }
}
