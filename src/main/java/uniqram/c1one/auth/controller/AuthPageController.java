package uniqram.c1one.auth.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuthPageController {

    // 로그인 페이지
    @GetMapping("/signin")
    public String showSignForm() {
        return "signin";
    }

}
