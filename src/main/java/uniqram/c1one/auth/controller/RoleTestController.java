package uniqram.c1one.auth.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class RoleTestController {

    @SecurityRequirement(name = "JWT")
    @GetMapping("/user/test")
    public String userOnly() {
        return "USER 접근 성공!";
    }

    @SecurityRequirement(name = "JWT")
    @GetMapping("/admin/test")
    public String adminOnly() {
        return "ADMIN 접근 성공!";
    }
}