package uniqram.c1one.dm.exception;

import uniqram.c1one.global.exception.BaseException;
import uniqram.c1one.global.exception.ErrorCode;

public class DmException extends BaseException {
    public DmException(ErrorCode errorCode) { super(errorCode); }
}
