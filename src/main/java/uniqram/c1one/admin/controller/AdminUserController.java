package uniqram.c1one.admin.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import uniqram.c1one.admin.dto.UserSummaryResponse;
import uniqram.c1one.admin.service.AdminUserService;
import uniqram.c1one.security.adapter.CustomUserDetails;

import java.util.List;

@RestController
@RequestMapping("/api/admin/users")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")

public class AdminUserController {

    private final AdminUserService adminUserService;

    @GetMapping
    public ResponseEntity<List<UserSummaryResponse>> getAllUsers() {
        return ResponseEntity.ok(adminUserService.getAllUsers());
    }

    @GetMapping("/online")
    public ResponseEntity<List<UserSummaryResponse>> getOnlineUsers() {
        return ResponseEntity.ok(adminUserService.getOnlineUsers());
    }

    @PostMapping("/blacklist/{userId}")
    public ResponseEntity<UserSummaryResponse> blacklistUserById(@PathVariable Long userId) {
        return ResponseEntity.ok(adminUserService.blacklistUserById(userId));
    }

    @PostMapping("/unblacklist/{userId}")
    public ResponseEntity<UserSummaryResponse> unblacklistUserById(@PathVariable Long userId) {
        return ResponseEntity.ok(adminUserService.unblacklistUserById(userId));
    }
}