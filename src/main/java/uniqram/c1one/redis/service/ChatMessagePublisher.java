package uniqram.c1one.redis.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import uniqram.c1one.dm.dto.ChatMessageResponse;

@Service
@RequiredArgsConstructor
public class ChatMessagePublisher {
    private final RedisTemplate<String, Object> redisTemplate;

    public void publish(String chatRoomId, ChatMessageResponse message) {
        redisTemplate.convertAndSend("chatroom: " + chatRoomId, message);
    }
}
