package uniqram.c1one.profile.exception;

import lombok.Getter;

@Getter
public class ProfileException extends RuntimeException {

    private final ProfileErrorCode errorCode;

    public ProfileException(ProfileErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public ProfileException(ProfileErrorCode errorCode, Throwable cause) {
        super(errorCode.getMessage(), cause);
        this.errorCode = errorCode;
    }
}
