package org.chinoel.goormspring.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.chinoel.goormspring.dto.request.SignUpRequest;
import org.chinoel.goormspring.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    @GetMapping("/signup")
    public String signupForm() {
        return "auth/signup";
    }

    @PostMapping("/auth/signup")
    public String signup(@Valid SignUpRequest request, BindingResult result) {
        if (result.hasErrors()) {
            return "auth/signup";
        }

        userService.signup(request);
        return "redirect:/login";
    }
}
