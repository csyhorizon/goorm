package uniqram.c1one.follow.dto;

public record FollowDto(
        Long profileId,
        Long userId,
        String username,
        String profileImageUrl,
        String bio
) {}