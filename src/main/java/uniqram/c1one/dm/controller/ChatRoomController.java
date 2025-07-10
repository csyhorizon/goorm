package uniqram.c1one.dm.controller;

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
@RequestMapping("/chatrooms")
@RequiredArgsConstructor
public class ChatRoomController {
    private final ChatRoomService chatRoomService;
    private final ChatMessageService chatMessageService;

    @PostMapping
    public ResponseEntity<ChatRoomCreateResponse> createChatRoom(
            @RequestBody ChatRoomCreateRequest request,
            @AuthenticationPrincipal CustomUserDetails userDetails) {
        Long userId = userDetails.getUserId();
        return ResponseEntity.ok(chatRoomService.createChatRoom(userId, request));
    }

    @GetMapping
    public ResponseEntity<List<ChatRoomList>> getMyChatRooms(
            @AuthenticationPrincipal CustomUserDetails userDetails
            ) {
        Long userId = userDetails.getUserId();
        return ResponseEntity.ok(chatRoomService.getMyChatRooms(userId));
    }

    @PostMapping("/{chatRoomId}/messages")
    public ResponseEntity<ChatMessageResponse> sendMessage(
            @PathVariable("chatRoomId") Long chatRoomId,
            @RequestBody ChatMessageRequeset requeset,
            @AuthenticationPrincipal CustomUserDetails userDetails) {
        Long senderId = userDetails.getUserId();
        return ResponseEntity.ok(chatMessageService.sendMessage(chatRoomId, senderId, requeset));
    }

    @GetMapping("/{chatRoomId}/messages")
    public ResponseEntity<List<ChatMessageList>> getChatMessages(
            @PathVariable Long chatRoomId,
            @RequestParam(defaultValue = "0") int offset,
            @RequestParam(defaultValue = "20") int limit
    ){
        return ResponseEntity.ok(chatMessageService.getChatMessages(chatRoomId, offset, limit));
    }

}
