package uniqram.c1one.post.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uniqram.c1one.global.success.SuccessResponse;
import uniqram.c1one.post.dto.HomePostResponse;
import uniqram.c1one.post.dto.PostRequest;
import uniqram.c1one.post.dto.PostResponse;
import uniqram.c1one.post.dto.UserPostResponse;
import uniqram.c1one.post.exception.PostSuccessCode;
import uniqram.c1one.post.service.PostService;

@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping
    public ResponseEntity<SuccessResponse<PostResponse>> createPost(
            @RequestBody @Valid PostRequest postRequest
    ) {
        Long userId = postRequest.getUserId();
        PostResponse postResponse = postService.createPost(userId, postRequest);
        return ResponseEntity.status(PostSuccessCode.POST_CREATED.getStatus())
                .body(SuccessResponse.of(PostSuccessCode.POST_CREATED, postResponse));
    }

    @GetMapping("/posts/home")
    public ResponseEntity<Page<HomePostResponse>> getHomePosts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Page<HomePostResponse> homePosts = postService.getHomePosts(page, size);
        return ResponseEntity.ok(homePosts);
    }

    @GetMapping("/profile/{userId}")
    public ResponseEntity<Page<UserPostResponse>> getUserPosts(
            @PathVariable Long userId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Page<UserPostResponse> posts = postService.getUserPosts(userId, page, size);
        return ResponseEntity.ok(posts);
    }
}
