package uniqram.c1one.dm.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import uniqram.c1one.dm.entity.ChatRoomType;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class ChatRoomList {

    private Long chatroomId;
    private ChatRoomType type;
    private LocalDateTime createdAt;
    private String lastMessage;
    private LocalDateTime lastMessageAt;
    private List<MemberDto> members;

    @Getter
    @Builder
    @AllArgsConstructor
    public static class MemberDto{
        private Long userId;
        private String username;
    }

}
