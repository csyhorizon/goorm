package uniqram.c1one.post.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uniqram.c1one.global.success.SuccessResponse;
import uniqram.c1one.post.dto.*;
import uniqram.c1one.post.exception.PostSuccessCode;
import uniqram.c1one.post.service.PostLikeService;
import uniqram.c1one.post.service.PostService;

@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;
    private final PostLikeService postLikeService;

    @PostMapping
    public ResponseEntity<SuccessResponse<PostResponse>> createPost(
            @RequestBody @Valid PostCreateRequest postCreateRequest
    ) {
        Long userId = postCreateRequest.getUserId();
        PostResponse postResponse = postService.createPost(userId, postCreateRequest);
        return ResponseEntity.status(PostSuccessCode.POST_CREATED.getStatus())
                .body(SuccessResponse.of(PostSuccessCode.POST_CREATED, postResponse));
    }

    @GetMapping("/home/{userId}")
    public ResponseEntity<Page<HomePostResponse>> getHomePosts(
            @PathVariable Long userId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Page<HomePostResponse> homePosts = postService.getHomePosts(userId, page, size);
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

    @PatchMapping("/{postId}")
    public ResponseEntity<PostResponse> updatePost(
            @PathVariable Long postId,
            @RequestBody PostUpdateRequest postUpdateRequest
    ) {
        Long userId = postUpdateRequest.getUserId();
        PostResponse updatedPost = postService.updatePost(userId, postId, postUpdateRequest);
        return ResponseEntity.ok(updatedPost);
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<Void> deletePost(
            @RequestParam Long userId,
            @PathVariable Long postId
    ) {
        postService.deletePost(userId, postId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{postId}/like")
    public ResponseEntity<PostLikeResponse> likePost(
            @PathVariable Long postId,
            @RequestParam Long userId
    ){
        PostLikeResponse like = postLikeService.like(userId, postId);
        return ResponseEntity.ok(like);
    }
}
