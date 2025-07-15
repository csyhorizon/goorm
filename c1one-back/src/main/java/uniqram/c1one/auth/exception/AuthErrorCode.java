package uniqram.c1one.auth.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import uniqram.c1one.global.exception.ErrorCode;

@Getter
@AllArgsConstructor
public enum AuthErrorCode implements ErrorCode {

    PASSWORD_MISMATCH(HttpStatus.BAD_REQUEST, "비밀번호가 일치하지 않습니다."),
    USERNAME_ALREADY_EXISTS(HttpStatus.CONFLICT, "이미 존재하는 사용자입니다."),
    USER_BLACKLISTED(HttpStatus.FORBIDDEN, "블랙리스트에 등록된 사용자입니다."),
    INVALID_CREDENTIALS(HttpStatus.UNAUTHORIZED, "사용자 인증 정보가 올바르지 않습니다."),
    SIGNIN_FAILED(HttpStatus.UNAUTHORIZED, "로그인 실패");

    private final HttpStatus status;
    private final String message;
}
