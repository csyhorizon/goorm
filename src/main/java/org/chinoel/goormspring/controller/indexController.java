package org.chinoel.goormspring.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Slf4j
@Controller
public class indexController {

    @GetMapping(value = "/")
    public String indexPageReturn() {
        return "index";
    }

    @GetMapping(value = "/login")
    public String login(
            @RequestParam(value = "error", required = false) String error,
            @RequestParam(value = "message", required = false) String message,
            Model model) {
        log.debug("Login Page accessed - error : {}, message : {}", error, message);
        if (error != null) {
            model.addAttribute("errorMessage", message);
        }
        return "auth/login";
    }
}