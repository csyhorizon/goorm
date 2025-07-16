package com.example.backend.domain.auth.exception;

import com.example.backend.domain.global.exception.BaseException;
import com.example.backend.domain.global.exception.ErrorCode;

public class AuthException extends BaseException {
    public AuthException(ErrorCode errorCode) {
        super(errorCode);
    }
}