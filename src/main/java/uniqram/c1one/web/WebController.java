package uniqram.c1one.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebController {

    @GetMapping("/")
    public String home() {
        return "redirect:/api/auth/signin";
    }

    @GetMapping("/index")
    public String index() {
        return "index";
    }

}
