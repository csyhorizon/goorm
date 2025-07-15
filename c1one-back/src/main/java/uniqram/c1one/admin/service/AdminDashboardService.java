package uniqram.c1one.admin.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import uniqram.c1one.admin.dto.DashboardResponse;
import uniqram.c1one.redis.service.ActiveUserService;
import uniqram.c1one.user.repository.UserRepository;

import java.util.Objects;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AdminDashboardService {

    private final UserRepository userRepository;
    private final StringRedisTemplate redisTemplate;
    private final ActiveUserService activeUserService;

    public DashboardResponse getDashboardStats() {
        long userCount = userRepository.count();
        long postCount = getApproxCount("post:*");
        long commentCount = getApproxCount("comment:*");
        long postLikeCount = getTotalLikeCount("post:like:*");
        long commentLikeCount = getTotalLikeCount("comment:like:*");
        long onlineUserCount = activeUserService.countActiveUsers();

        return DashboardResponse.builder()
                .userCount(userCount)
                .postCount(postCount)
                .commentCount(commentCount)
                .postLikeCount(postLikeCount)
                .commentLikeCount(commentLikeCount)
                .onlineUserCount(onlineUserCount)
                .build();
    }

    private int getTotalLikeCount(String pattern) {
        Set<String> keys = redisTemplate.keys(pattern);
        if (keys.isEmpty()) return 0;

        return keys.stream()
                .map(redisTemplate.opsForValue()::get)
                .filter(Objects::nonNull)
                .mapToInt(Integer::parseInt)
                .sum();
    }

    private long getApproxCount(String pattern) {
        Set<String> keys = redisTemplate.keys(pattern);
        return keys.size();
    }
}
