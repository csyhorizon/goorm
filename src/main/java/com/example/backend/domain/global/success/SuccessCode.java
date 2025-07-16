package com.example.backend.domain.global.success;

import org.springframework.http.HttpStatus;

public interface SuccessCode {
    HttpStatus getStatus();
    String getMessage();
}
