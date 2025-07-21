package uniqram.c1one.dm.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import uniqram.c1one.dm.dto.*;
import uniqram.c1one.dm.service.ChatMessageService;
import uniqram.c1one.dm.service.ChatRoomService;
import uniqram.c1one.security.adapter.CustomUserDetails;

import java.util.List;

@RestController
@RequestMapping("/api/chatrooms")
@RequiredArgsConstructor
public class ChatRoomController {
    private final ChatRoomService chatRoomService;
    private final ChatMessageService chatMessageService;

    @Operation(summary = "채팅방 생성", description = "채팅방을 생성합니다.")
    @ApiResponse(responseCode = "201", description = "셍성 성공")
    @PostMapping
    public ResponseEntity<ChatRoomCreateResponse> createChatRoom(
            @RequestBody ChatRoomCreateRequest request,
            @AuthenticationPrincipal CustomUserDetails userDetails) {
        Long userId = userDetails.getUserId();
        return ResponseEntity.ok(chatRoomService.createChatRoom(userId, request));
    }

    @Operation(summary = "채팅방 조회", description = "생성된 채팅방을 조회합니다.")
    @ApiResponse(responseCode = "200", description = "성공")
    @GetMapping
    public ResponseEntity<List<ChatRoomList>> getMyChatRooms(
            @AuthenticationPrincipal CustomUserDetails userDetails
            ) {
        Long userId = userDetails.getUserId();
        return ResponseEntity.ok(chatRoomService.getMyChatRooms(userId));
    }

    @Operation(summary = "메세지 전송", description = "메세지를 전송합니다.")
    @ApiResponse(responseCode = "200", description = "전송 성공")
    @PostMapping("/{chatRoomId}/messages")
    public ResponseEntity<ChatMessageResponse> sendMessage(
            @PathVariable("chatRoomId") Long chatRoomId,
            @RequestBody ChatMessageDto requeset,
            @AuthenticationPrincipal CustomUserDetails userDetails) {
        Long senderId = userDetails.getUserId();
        return ResponseEntity.ok(chatMessageService.sendMessage(chatRoomId, senderId, requeset));
    }


    @Operation(summary = "채팅메세지 조회", description = "채팅방 내의 메세지를 조회합니다.")
    @ApiResponse(responseCode = "200", description = "성공")
    @GetMapping("/{chatRoomId}/messages")
    public ResponseEntity<List<ChatMessageList>> getChatMessages(
            @PathVariable Long chatRoomId,
            @RequestParam(defaultValue = "0") int offset,
            @RequestParam(defaultValue = "20") int limit
    ){
        return ResponseEntity.ok(chatMessageService.getChatMessages(chatRoomId, offset, limit));
    }

    @Operation(summary = "메세지 삭제", description = "특정 메세지를 삭제합니다.")
    @ApiResponse(responseCode = "204", description = "삭제 성공")
    @ApiResponse(responseCode = "404", description = "메세지를 찾을 수 없습니다.")
    @ApiResponse(responseCode = "403", description = "수정 또는 삭제 권한이 없습니다.")
    @DeleteMapping("/{chatRoomId}/messages/{messageId}")
    public ResponseEntity<Void> deleteChatMessage(
            @PathVariable Long chatRoomId,
            @PathVariable Long messageId,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        Long userId = userDetails.getUserId();
        chatMessageService.deleteChatMessage(chatRoomId, messageId, userId);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "메세지 수정", description = "특정 메세지를 수정합니다.")
    @ApiResponse(responseCode = "200", description = "수정 성공")
    @ApiResponse(responseCode = "404", description = "메세지를 찾을 수 없습니다.")
    @ApiResponse(responseCode = "403", description = "수정 또는 삭제 권한이 없습니다.")
    @PatchMapping("/{chatRoomId}/messages/{messageId}")
    public ResponseEntity<ChatMessageResponse> updateChatMessage(
            @PathVariable Long chatRoomId,
            @PathVariable Long messageId,
            @RequestBody ChatMessageRequeset updateRequest,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        Long userId = userDetails.getUserId();
        ChatMessageResponse response = chatMessageService.updateMessage(chatRoomId, messageId, userId, updateRequest);
        return ResponseEntity.ok(response);
    }

}
