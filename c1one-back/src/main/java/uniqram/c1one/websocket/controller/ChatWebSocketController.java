package uniqram.c1one.websocket.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import uniqram.c1one.dm.dto.ChatMessageDto;
import uniqram.c1one.dm.dto.ChatMessageResponse;
import uniqram.c1one.dm.service.ChatMessageService;
import uniqram.c1one.redis.service.ChatMessagePublisher;
import uniqram.c1one.security.adapter.CustomUserDetails;
import uniqram.c1one.user.entity.Users;
import uniqram.c1one.user.repository.UserRepository;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class ChatWebSocketController {
    private final ChatMessagePublisher chatMessagePublisher; //Redis Pub/Sub
    private final ChatMessageService chatMessageService;
    private final UserRepository userRepository;


    @MessageMapping("/{chatroomId}") // /app/{chatroomId}로 전송 시 실행
    public void sendMessage(
            @DestinationVariable Long chatroomId,
            @Payload ChatMessageDto message,
            Principal principal) {
        String username = principal.getName();
        Users user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));
        Long senderId = user.getId();
        ChatMessageResponse response = chatMessageService.sendMessage(chatroomId, senderId, message);
        chatMessagePublisher.publish(message.getChatroomId().toString(), response);
    }
}
