package uniqram.c1one.admin.dto;

import lombok.Builder;
import lombok.Getter;
import uniqram.c1one.user.entity.Users;

@Getter
@Builder

public class UserSummaryResponse {
    private Long id;
    private String username;
    private String role;

    public static UserSummaryResponse from(Users user) {
        return UserSummaryResponse.builder()
                .id(user.getId())
                .username(user.getUsername())
                .role(user.getRole().name())
                .build();
    }
}