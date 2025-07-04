package uniqram.c1one.admin.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uniqram.c1one.admin.dto.UserSummaryResponse;
import uniqram.c1one.redis.service.ActiveUserService;
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
}
