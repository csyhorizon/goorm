package uniqram.c1one.auth.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class RoleTestController {

    @GetMapping("/user/test")
    public String userOnly() {
        return "USER 접근 성공!";
    }

    @GetMapping("/admin/test")
    public String adminOnly() {
        return "ADMIN 접근 성공!";
    }
}