package uniqram.c1one.post.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uniqram.c1one.global.success.SuccessResponse;
import uniqram.c1one.post.dto.PostRequest;
import uniqram.c1one.post.dto.PostResponse;
import uniqram.c1one.post.exception.PostSuccessCode;
import uniqram.c1one.post.service.PostService;

@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping
    public ResponseEntity<SuccessResponse<PostResponse>> createPost(@RequestBody PostRequest postRequest) {
        Long userId = postRequest.getUserId();
        PostResponse postResponse = postService.createPost(userId, postRequest);
        return ResponseEntity.status(PostSuccessCode.POST_CREATED.getStatus())
                .body(SuccessResponse.of(PostSuccessCode.POST_CREATED, postResponse));
    }
}
