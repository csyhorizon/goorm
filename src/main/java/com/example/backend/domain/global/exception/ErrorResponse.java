package com.example.backend.domain.global.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ErrorResponse {
    private int status;
    private String message;

    public static ErrorResponse of(ErrorCode errorCode) {
        return new ErrorResponse(errorCode.getStatus().value(), errorCode.getMessage());
    }

    public static ErrorResponse of(ErrorCode errorCode, String customMessage) {
        return new ErrorResponse(errorCode.getStatus().value(), customMessage);
    }
}
