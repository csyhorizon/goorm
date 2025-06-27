package uniqram.c1one.follow.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uniqram.c1one.follow.dto.FollowRequestDto;
import uniqram.c1one.follow.entity.Follow;
import uniqram.c1one.follow.service.FollowService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class FollowController {

    private final FollowService followService;

    // 모든 DTO는 자신 호출의 임시 방편이고, @AuthenticationPrincipal로 추후에 변경 필요

    @PostMapping("follows/{targetUserId}")
    public ResponseEntity<Void> createFollow(
            @PathVariable Long targetUserId,
            @RequestBody FollowRequestDto requestDto
    ) {
        followService.createFollow(requestDto.getMyId(), targetUserId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("follows/{targetUserId}")
    public ResponseEntity<Void> unfollow(
            @RequestBody FollowRequestDto requestDto,
            @PathVariable Long targetUserId
    ) {
        followService.unfollow(requestDto.getMyId(), targetUserId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("follows/{targetUserId}")
    public ResponseEntity<Void> removeFollower(
            @RequestBody FollowRequestDto requestDto,
            @PathVariable Long targetUserId
    ) {
        followService.removeFollower(requestDto.getMyId(), targetUserId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/users/{userId}/followings")
    public ResponseEntity<List<Follow>> getFollowings(@PathVariable Long userId) {
        return ResponseEntity.ok(followService.getFollowings(userId));
    }

    @GetMapping("/users/{userId}/followers")
    public ResponseEntity<List<Follow>> getFollowers(@PathVariable Long userId) {
        return ResponseEntity.ok(followService.getFollowers(userId));
    }
}