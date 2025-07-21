package uniqram.c1one.comment.exception;

import uniqram.c1one.global.exception.BaseException;
import uniqram.c1one.global.exception.ErrorCode;

public class CommentException extends BaseException {
    public CommentException(ErrorCode errorCode) { super(errorCode); }
}
