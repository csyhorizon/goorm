package com.example.backend.domain.store.controller;

import com.example.backend.domain.event.dto.EventCreateRequest;
import com.example.backend.domain.event.dto.EventResponse;
import com.example.backend.domain.event.service.EventService;
import com.example.backend.domain.item.dto.ItemCreateRequest;
import com.example.backend.domain.item.dto.ItemResponse;
import com.example.backend.domain.item.service.ItemService;
import com.example.backend.domain.auth.jwt.security.CustomUserDetails;
import com.example.backend.domain.store.dto.StoreResponse;
import com.example.backend.domain.store.service.StoreQueryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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
public class StoreQueryController {
    private final StoreQueryService storeService;
    private final ItemService itemService;
    private final EventService eventService;

    @Operation(summary = "가게 상세조회", description = "가게를 상세 조회합니다.")
    @ApiResponse(responseCode = "200", description = "가게 상세조회 성공")
    @GetMapping("/{storeId}")
    public ResponseEntity<StoreResponse> getStoreDetail(@PathVariable("storeId") Long storeId) {
        return ResponseEntity.ok(storeService.findById(storeId));
    }

    //todo 아래의 코드들을 상품, 이벤트 도메인을 분리하면서 작업 예정
    @Operation(summary = "가게의 상품 등록", description = "사장님이 가게에 판매상품을 등록합니다.")
    @ApiResponse(responseCode = "200", description = "상품 등록 성공")
    @PostMapping("/{storeId}/items")
    public ResponseEntity<ItemResponse> createStoreItem(@AuthenticationPrincipal CustomUserDetails user,
                                                        @PathVariable("storeId") Long storeId,
                                                        @RequestBody ItemCreateRequest itemCreateRequest) {
        return ResponseEntity.ok(itemService.save(user.getUserId(), storeId, itemCreateRequest));
    }

    @Operation(summary = "가게에 등록된 상품들 조회", description = "가게에 등록된 상품들을 조회합니다.")
    @ApiResponse(responseCode = "200", description = "상품목록 조회 성공")
    @GetMapping("/{storeId}/items")
    public ResponseEntity<List<ItemResponse>> getStoreItems(@PathVariable("storeId") Long storeId) {
        return ResponseEntity.ok(itemService.findAllByStoreId(storeId));
    }

    @Operation(summary = "상품 삭제", description = "사장님이 가게에 등록된 상품을 삭제합니다.")
    @ApiResponse(responseCode = "200", description = "상품 삭제 성공")
    @DeleteMapping("/{storeId}/{itemId}")
    public ResponseEntity<Void> deleteItem(@AuthenticationPrincipal CustomUserDetails user,
                                           @PathVariable("storeId") Long storeId,
                                           @PathVariable("itemId") Long itemId) {
        itemService.deleteItem(user.getUserId(), storeId, itemId);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "이벤트 등록", description = "사장님이 이벤트를 등록합니다.")
    @ApiResponse(responseCode = "200", description = "이벤트 등록 성공")
    @PostMapping("/{storeId}/events")
    public ResponseEntity<EventResponse> createEvents(@AuthenticationPrincipal CustomUserDetails user,
                                                      @PathVariable("storeId") Long storeId,
                                                      @RequestBody EventCreateRequest eventCreateRequest) {
        return ResponseEntity.ok(eventService.save(user.getUserId(), storeId, eventCreateRequest));
    }

    @Operation(summary = "이벤트 목록 조회", description = "가게가 등록했던 이벤트들을 조회합니다.")
    @ApiResponse(responseCode = "200", description = "이벤트 목록 조회 성공")
    @GetMapping("/{storeId}/events")
    public ResponseEntity<List<EventResponse>> getEvents(@PathVariable("storeId") Long storeId) {
        return ResponseEntity.ok(eventService.getAllEvents(storeId));
    }

    @Operation(summary = "이벤트 적용된 상품정보 조회", description = "이벤트가 적용된 상품의 정보를 조회합니다. (할인)")
    @ApiResponse(responseCode = "200", description = "이벤트 적용된 상품정보 조회 성공")
    @GetMapping("/{storeId}/{eventId}/items")
    public ResponseEntity<List<ItemResponse>> getItemListWithEvent(@PathVariable("storeId") Long storeId,
                                                                   @PathVariable("eventId") Long eventId) {
        return ResponseEntity.ok(itemService.getItemsWithEvent(storeId, eventId));
    }
}
