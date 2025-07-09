package uniqram.c1one.follow.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uniqram.c1one.follow.dto.FollowDto;
import uniqram.c1one.follow.service.FollowService;
import uniqram.c1one.security.adapter.CustomUserDetails;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class FollowController {

    private final FollowService followService;

    @PostMapping("follows/{targetUserId}")
    public ResponseEntity<Void> createFollow(
            @PathVariable Long targetUserId,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        followService.createFollow(userDetails.getUserId(), targetUserId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("follows/{targetUserId}")
    public ResponseEntity<Void> unfollow(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @PathVariable Long targetUserId
    ) {
        followService.unfollow(userDetails.getUserId(), targetUserId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("followers/{targetUserId}")
    public ResponseEntity<Void> removeFollower(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @PathVariable Long targetUserId
    ) {
        followService.removeFollower(userDetails.getUserId(), targetUserId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/users/{userId}/followings")
    public ResponseEntity<List<FollowDto>> getFollowings(@PathVariable Long userId) {
        return ResponseEntity.ok(followService.getFollowings(userId));
    }

    @GetMapping("/users/{userId}/followers")
    public ResponseEntity<List<FollowDto>> getFollowers(@PathVariable Long userId) {
        return ResponseEntity.ok(followService.getFollowers(userId));
    }
}