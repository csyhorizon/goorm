package uniqram.c1one.dm.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
public class ChatMessageList {
    private Long messageId;
    private Long senderId;
    private String senderName;
    private String message;
    private LocalDateTime createdAt;
    private Boolean isRead;
}
