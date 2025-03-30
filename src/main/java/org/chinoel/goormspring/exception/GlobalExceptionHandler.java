package org.chinoel.goormspring.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    public String IllegalArgumentException(IllegalArgumentException e, Model model, HttpServletRequest request) {
        model.addAttribute("errorMessage", e.getMessage());

        return request.getRequestURI();
    }
}
