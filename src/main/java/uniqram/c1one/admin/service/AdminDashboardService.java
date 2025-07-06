package uniqram.c1one.admin.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uniqram.c1one.admin.dto.DashboardResponse;
import uniqram.c1one.comment.repository.CommentRepository;
import uniqram.c1one.comment.repository.CommentLikeRepository;
import uniqram.c1one.post.repository.PostRepository;
import uniqram.c1one.post.repository.PostLikeRepository;
import uniqram.c1one.redis.service.ActiveUserService;
import uniqram.c1one.user.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class AdminDashboardService {

    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final PostLikeRepository postLikeRepository;
    private final CommentLikeRepository commentLikeRepository;
    private final ActiveUserService activeUserService;

    public DashboardResponse getDashboardStats() {
        return DashboardResponse.builder()
                .userCount(userRepository.count())
                .postCount(postRepository.count())
                .commentCount(commentRepository.count())
                .postLikeCount(postLikeRepository.count())
                .commentLikeCount(commentLikeRepository.count())
                .onlineUserCount(activeUserService.countActiveUsers())
                .build();
    }
}