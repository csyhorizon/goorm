package uniqram.c1one.web;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebController {

    @GetMapping(value = {"/index", "/index.html"})
    public String index(@AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails != null) {
            return "index";  // 인증된 사용자인 경우
        }
        return "redirect:/api/auth/signin";  // 인증되지 않은 경우
    }


}
