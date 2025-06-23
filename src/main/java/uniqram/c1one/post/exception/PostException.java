package uniqram.c1one.post.exception;

import uniqram.c1one.global.exception.BaseException;
import uniqram.c1one.global.exception.ErrorCode;

public class PostException extends BaseException {
    public PostException(ErrorCode errorCode) { super(errorCode); }
}
