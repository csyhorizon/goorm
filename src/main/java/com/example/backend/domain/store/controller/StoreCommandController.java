package com.example.backend.domain.store.controller;

import com.example.backend.domain.auth.jwt.security.CustomUserDetails;
import com.example.backend.domain.event.dto.EventCreateRequest;
import com.example.backend.domain.event.dto.EventResponse;
import com.example.backend.domain.event.service.command.EventCommandService;
import com.example.backend.domain.item.dto.ItemCreateRequest;
import com.example.backend.domain.item.dto.ItemResponse;
import com.example.backend.domain.item.service.command.ItemCommandService;
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
    private final ItemCommandService itemCommandService;
    private final EventCommandService eventCommandService;

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

    //todo 아래의 코드들을 상품, 이벤트 도메인을 분리하면서 작업 예정
    @Operation(summary = "가게의 상품 등록", description = "사장님이 가게에 판매상품을 등록합니다.")
    @ApiResponse(responseCode = "200", description = "상품 등록 성공")
    @PostMapping("/{storeId}/items")
    public ResponseEntity<ItemResponse> createStoreItem(@AuthenticationPrincipal CustomUserDetails user,
                                                        @PathVariable("storeId") Long storeId,
                                                        @RequestBody ItemCreateRequest itemCreateRequest) {
        return ResponseEntity.ok(itemCommandService.save(user.getUserId(), storeId, itemCreateRequest));
    }

    @Operation(summary = "상품 삭제", description = "사장님이 가게에 등록된 상품을 삭제합니다.")
    @ApiResponse(responseCode = "200", description = "상품 삭제 성공")
    @DeleteMapping("/{storeId}/{itemId}")
    public ResponseEntity<Void> deleteItem(@AuthenticationPrincipal CustomUserDetails user,
                                           @PathVariable("storeId") Long storeId,
                                           @PathVariable("itemId") Long itemId) {
        itemCommandService.delete(user.getUserId(), storeId, itemId);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "이벤트 등록", description = "사장님이 이벤트를 등록합니다.")
    @ApiResponse(responseCode = "200", description = "이벤트 등록 성공")
    @PostMapping("/{storeId}/events")
    public ResponseEntity<EventResponse> createEvents(@AuthenticationPrincipal CustomUserDetails user,
                                                      @PathVariable("storeId") Long storeId,
                                                      @RequestBody EventCreateRequest eventCreateRequest) {
        return ResponseEntity.ok(eventCommandService.save(user.getUserId(), storeId, eventCreateRequest));
    }
}
