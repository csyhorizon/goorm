package uniqram.c1one.redis.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;
import uniqram.c1one.dm.dto.ChatMessageDto;
import uniqram.c1one.dm.dto.ChatMessageRequeset;

import java.nio.charset.StandardCharsets;

@Component
@RequiredArgsConstructor
public class ChatMessageSubscriber implements MessageListener {
    private final ObjectMapper objectMapper;
    private final SimpMessagingTemplate messagingTemplate;


    @Override
    public void onMessage(Message message, byte[] pattern) {
        try {
            String msgJson = new String(message.getBody(), StandardCharsets.UTF_8);
            ChatMessageDto chatMessage = objectMapper.readValue(msgJson, ChatMessageDto.class);

            // 1. 실시간 WebSocket으로 전송
            String topic = "/topic/chatroom." + chatMessage.getChatroomId();
            messagingTemplate.convertAndSend(topic, chatMessage);

            // 2. (옵션) 알림, 통계, DB 기록 등 추가 작업

        } catch (Exception e) {
            // 에러 로깅/핸들링
        }
    }
}
