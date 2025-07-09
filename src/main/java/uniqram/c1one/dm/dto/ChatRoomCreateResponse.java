package uniqram.c1one.dm.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class ChatRoomCreateResponse {
    private Long chatRoomId;
    private List<MemberDto> members;

    @Getter
    @Builder
    public static class MemberDto {
        private Long userId;
        private String username;
    }
}
