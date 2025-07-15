package uniqram.c1one.admin.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uniqram.c1one.admin.dto.UserSummaryResponse;
import uniqram.c1one.redis.service.ActiveUserService;
import uniqram.c1one.security.adapter.CustomUserDetails;
import uniqram.c1one.user.entity.Users;
import uniqram.c1one.user.repository.UserRepository;

import java.util.List;

@Service
@RequiredArgsConstructor

public class AdminUserService {

    private final UserRepository userRepository;
    private final ActiveUserService activeUserService;

    public List<UserSummaryResponse> getAllUsers() {
        return userRepository.findAll().stream()
                .map(UserSummaryResponse::from)
                .toList();
    }

    public List<UserSummaryResponse> getOnlineUsers() {
        return activeUserService.getAllActiveUsers().stream()
                .map(UserSummaryResponse::from)
                .toList();
    }

    @Transactional
    public UserSummaryResponse blacklistUser(CustomUserDetails userDetails) {
        Long userId = userDetails.getUserId();
        return blacklistUserById(userId);
    }

    @Transactional
    public UserSummaryResponse blacklistUserById(Long userId) {
        Users user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));

        // Set user as blacklisted
        user.setBlacklisted(true);

        // If user is currently active, log them out
        activeUserService.removeActiveUser(userId);

        return UserSummaryResponse.from(user);
    }

    @Transactional
    public UserSummaryResponse unblacklistUser(CustomUserDetails userDetails) {
        Long userId = userDetails.getUserId();
        return unblacklistUserById(userId);
    }

    @Transactional
    public UserSummaryResponse unblacklistUserById(Long userId) {
        Users user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));

        // Remove user from blacklist
        user.setBlacklisted(false);

        return UserSummaryResponse.from(user);
    }
}
