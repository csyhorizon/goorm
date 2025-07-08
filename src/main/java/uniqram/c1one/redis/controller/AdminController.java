package uniqram.c1one.redis.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uniqram.c1one.redis.model.ActiveUser;
import uniqram.c1one.redis.service.ActiveUserService;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {

    private final ActiveUserService activeUserService;

    @GetMapping("/active-users")
    @PreAuthorize("hasRole('ADMIN')")
    public List<ActiveUser> getActiveUsers() {
        return activeUserService.getAllActiveUsers();
    }
}
