package uniqram.c1one.post.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uniqram.c1one.post.dto.PostRequest;
import uniqram.c1one.post.dto.PostResponse;
import uniqram.c1one.post.entity.Post;
import uniqram.c1one.post.exception.PostErrorCode;
import uniqram.c1one.post.exception.PostException;
import uniqram.c1one.post.repository.PostRepository;
import uniqram.c1one.user.entity.Users;
import uniqram.c1one.user.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @Transactional
    public PostResponse createPost(Long userId, PostRequest postRequest) {
        Users user = userRepository.findById(userId)
                .orElseThrow(() -> new PostException(PostErrorCode.USER_NOT_FOUND));

        Post post = Post.of(user, postRequest.getContent(), postRequest.getLocation());

        postRepository.save(post);

        return PostResponse.builder()
                .postId(post.getId())
                .content(post.getContent())
                .location(post.getLocation())
                .build();
    }
}
