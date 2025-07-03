package uniqram.c1one.global.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import uniqram.c1one.comment.repository.CommentLikeRepository;
import uniqram.c1one.post.repository.PostLikeRepository;

@Service
@RequiredArgsConstructor
public class LikeCountService {

    private final StringRedisTemplate redisTemplate;
    private final PostLikeRepository postLikeRepository;
    private final CommentLikeRepository commentLikeRepository;

    private static final String POST_LIKE_KEY_PREFIX = "post:like:";
    private static final String COMMENT_LIKE_KEY_PREFIX = "comment:like:";

    public int getPostLikeCount(Long postId) {
        String key = POST_LIKE_KEY_PREFIX + postId;
        String value = redisTemplate.opsForValue().get(key);

        if(value != null) {
            return Integer.parseInt(value);
        } else {
            int count = postLikeRepository.countByPostId(postId);
            redisTemplate.opsForValue().set(key, String.valueOf(count));
            return count;
        }
    }

    public int getCommentLikeCount(Long commentId) {
        String key = COMMENT_LIKE_KEY_PREFIX + commentId;
        String value = redisTemplate.opsForValue().get(key);
        if(value != null) {
            return Integer.parseInt(value);
        } else {
            int count = commentLikeRepository.countByCommentId(commentId);
            redisTemplate.opsForValue().set(key, String.valueOf(count));
            return count;
        }
    }

    public void incrementPostLike(Long postId) {
        String key = POST_LIKE_KEY_PREFIX + postId;
        redisTemplate.opsForValue().increment(key, 1);
    }

    public void decrementPostLike(Long postId) {
        String key = POST_LIKE_KEY_PREFIX + postId;
        redisTemplate.opsForValue().decrement(key, 1);
    }

    public void incrementCommentLike(Long commentId) {
        String key = COMMENT_LIKE_KEY_PREFIX + commentId;
        redisTemplate.opsForValue().increment(key, 1);
    }

    public void decrementCommentLike(Long commentId) {
        String key = COMMENT_LIKE_KEY_PREFIX + commentId;
        redisTemplate.opsForValue().decrement(key, 1);
    }

}
