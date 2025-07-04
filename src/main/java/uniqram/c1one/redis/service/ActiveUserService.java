package uniqram.c1one.redis.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import uniqram.c1one.security.adapter.CustomUserDetails;
import uniqram.c1one.redis.model.ActiveUser;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ActiveUserService {

    private static final String ACTIVE_USERS_KEY = "active:users";
    private final RedisTemplate<String, Object> redisTemplate;

    public void addActiveUser(CustomUserDetails userDetails, String ipAddress) {
        System.out.println("addActiveUser 호출: " + userDetails.getUsername());
        try {
            ActiveUser user = new ActiveUser(
                    userDetails.getUserId(),
                    userDetails.getUsername(),
                    LocalDateTime.now(),
                    ipAddress,
                    LocalDateTime.now()
            );

            // userId를 키로 사용하여 저장
            redisTemplate.opsForHash().put(ACTIVE_USERS_KEY, String.valueOf(userDetails.getUserId()), user);

            // 만료 시간 설정
            redisTemplate.expire(ACTIVE_USERS_KEY, 1, TimeUnit.HOURS);
        } catch (Exception e) {
            System.err.println("Redis 오류: 온라인 사용자 추가 실패 " + e.getMessage());
        }


    }

    public void updateUserLastAccess(String userId) {
        ActiveUser user = (ActiveUser) redisTemplate.opsForHash().get(ACTIVE_USERS_KEY, userId);
        if (user != null) {
            user.setLastAccessTime(LocalDateTime.now());
            redisTemplate.opsForHash().put(ACTIVE_USERS_KEY, userId, user);
        }
    }

    public void removeActiveUser(Long sessionId) {
        redisTemplate.opsForHash().delete(ACTIVE_USERS_KEY, sessionId);
    }

    public List<ActiveUser> getAllActiveUsers() {
        return redisTemplate.opsForHash().values(ACTIVE_USERS_KEY)
                .stream()
                .map(obj -> (ActiveUser) obj)
                .collect(Collectors.toList());
    }

}
