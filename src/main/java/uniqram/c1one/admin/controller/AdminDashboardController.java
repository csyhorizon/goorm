package uniqram.c1one.admin.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uniqram.c1one.admin.dto.DashboardResponse;
import uniqram.c1one.admin.service.AdminDashboardService;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")

public class AdminDashboardController {

    private final AdminDashboardService adminDashboardService;

    @GetMapping("/dashboard")
    public ResponseEntity<DashboardResponse> getDashboardStats() {
        DashboardResponse response = adminDashboardService.getDashboardStats();
        return ResponseEntity.ok(response);
    }
}