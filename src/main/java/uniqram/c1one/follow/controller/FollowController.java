package uniqram.c1one.follow.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uniqram.c1one.follow.dto.FollowRequestDto;
import uniqram.c1one.follow.dto.FollowResponseDto;
import uniqram.c1one.follow.entity.Follow;
import uniqram.c1one.follow.service.FollowService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class FollowController {

    private final FollowService followService;

    @PostMapping("following")
    public ResponseEntity<FollowResponseDto> createFollow(
            @RequestBody FollowRequestDto request) {

        Follow follow = followService.createFollow(request.getFollowerId(), request.getFollowingId());
        FollowResponseDto response = new FollowResponseDto(follow);

        return ResponseEntity.ok(response);
    }
}