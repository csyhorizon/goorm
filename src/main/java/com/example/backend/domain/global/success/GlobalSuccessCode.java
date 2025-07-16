package com.example.backend.domain.global.success;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum GlobalSuccessCode implements SuccessCode{
    OK(HttpStatus.OK, "요청이 성공적으로 처리되었습니다."),
    CREATED(HttpStatus.CREATED, "요청이 성공적으로 생성되었습니다.");

    private final HttpStatus status;
    private final String message;
}
