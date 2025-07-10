package uniqram.c1one.dm.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class ChatMessageResponse {
    private Long chatMessageId;
    private Long senderId;
    private String message;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private Boolean isRead;
}
