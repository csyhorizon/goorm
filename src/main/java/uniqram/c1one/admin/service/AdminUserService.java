package uniqram.c1one.admin.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uniqram.c1one.admin.dto.UserSummaryResponse;
import uniqram.c1one.user.entity.Users;
import uniqram.c1one.user.repository.UserRepository;

import java.util.List;

@Service
@RequiredArgsConstructor

public class AdminUserService {

    private final UserRepository userRepository;

    public List<UserSummaryResponse> getAllUsers() {
        return userRepository.findAll().stream()
                .map(UserSummaryResponse::from)
                .toList();
    }

    public List<UserSummaryResponse> getOnlineUsers() {
        // 임시: 전체 유저 리턴. redis 적용 후 리펙토링 예정.
        return userRepository.findAll().stream()
                .map(UserSummaryResponse::from)
                .toList();
    }
}