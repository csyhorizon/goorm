package uniqram.c1one.websocket.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import uniqram.c1one.dm.dto.ChatMessageDto;
import uniqram.c1one.redis.service.ChatMessagePublisher;

@Controller
@RequiredArgsConstructor
public class ChatWebSocketController {
    private final ChatMessagePublisher chatMessagePublisher; //Redis Pub/Sub


    @MessageMapping("/chat.sendMessage") // /app/chat.sendMessage로 전송 시 실행
    public void sendMessage(ChatMessageDto message) {
        chatMessagePublisher.publish(message.getChatroomId().toString(), message);
    }
}
