package uniqram.c1one.auth.exception;

import uniqram.c1one.global.exception.BaseException;
import uniqram.c1one.global.exception.ErrorCode;

public class AuthException extends BaseException {
    public AuthException(ErrorCode errorCode) {
        super(errorCode);
    }
}