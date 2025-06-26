package uniqram.c1one.follow.dto;

import java.time.Instant;
import lombok.Getter;
import lombok.NoArgsConstructor;
import uniqram.c1one.follow.entity.Follow;

@Getter
@NoArgsConstructor
public class FollowResponseDto {
    private Long id;
    private Long followerId;
    private Long followingId;
    private Instant createdAt;

    public FollowResponseDto(Follow follow) {
        this.id = follow.getId();
        this.followerId = follow.getFollower().getId();
        this.followingId = follow.getFollowing().getId();
        this.createdAt = follow.getCreatedAt();
    }
}