package uniqram.c1one.post.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uniqram.c1one.post.dto.PostLikeResponse;
import uniqram.c1one.post.entity.Post;
import uniqram.c1one.post.entity.PostLike;
import uniqram.c1one.post.exception.PostErrorCode;
import uniqram.c1one.post.exception.PostException;
import uniqram.c1one.post.repository.PostLikeRepository;
import uniqram.c1one.post.repository.PostRepository;
import uniqram.c1one.user.entity.Users;
import uniqram.c1one.user.repository.UserRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostLikeService {
    private final PostLikeRepository postLikeRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @Transactional
    public PostLikeResponse like(Long userId, Long postId) {
        Users user = userRepository.findById(userId)
                .orElseThrow(() -> new PostException(PostErrorCode.USER_NOT_FOUND));
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new PostException(PostErrorCode.POST_NOT_FOUND));

        Optional<PostLike> existing = postLikeRepository.findByUserAndPost(user, post);

        boolean liked;
        if (existing.isPresent()) {
            postLikeRepository.delete(existing.get());
            liked = false;
        } else {
            PostLike postLike = PostLike.builder()
                    .user(user)
                    .post(post)
                    .build();
            postLikeRepository.save(postLike);
            liked = true;
        }
        int likeCount = postLikeRepository.countByPost(post);

        return PostLikeResponse.builder()
                .postId(postId)
                .liked(liked)
                .likeCount(likeCount)
                .build();
    }
}
