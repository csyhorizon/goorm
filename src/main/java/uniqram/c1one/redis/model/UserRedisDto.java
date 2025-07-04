package uniqram.c1one.redis.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import uniqram.c1one.user.entity.Users;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserRedisDto implements Serializable {

    private Long id;
    private String username;
    private LocalDateTime lastLoginTime;
    private UserStatus status;

    public static UserRedisDto from(Users user) {
        return UserRedisDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .lastLoginTime(LocalDateTime.now())
                .status(UserStatus.OFFLINE)
                .build();
    }
}
