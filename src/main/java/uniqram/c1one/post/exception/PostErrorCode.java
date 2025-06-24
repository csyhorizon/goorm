package uniqram.c1one.post.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import uniqram.c1one.global.exception.ErrorCode;

@Getter
@RequiredArgsConstructor
public enum PostErrorCode implements ErrorCode {
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "사용자를 찾을 수 없습니다."),
    IMAGE_REQUIRED(HttpStatus.BAD_REQUEST, "이미지는 필수 입력 값입니다."),
    POST_NOT_FOUND(HttpStatus.NOT_FOUND, "게시글을 찾을 수 없습니다."),
    NO_AUTHORITY(HttpStatus.FORBIDDEN, "수정 또는 삭제 권한이 없습니다.");

    private final HttpStatus status;
    private final String message;
}
