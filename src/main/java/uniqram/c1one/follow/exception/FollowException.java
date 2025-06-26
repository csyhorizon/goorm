package uniqram.c1one.follow.exception;

import lombok.Getter;

@Getter
public class FollowException extends RuntimeException {

    private final FollowErrorCode errorCode;

    public FollowException(FollowErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public FollowException(FollowErrorCode errorCode, Throwable cause) {
        super(errorCode.getMessage(), cause);
        this.errorCode = errorCode;
    }
}
