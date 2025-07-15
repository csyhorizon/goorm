package uniqram.c1one.comment.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import uniqram.c1one.global.success.SuccessCode;

@Getter
@RequiredArgsConstructor
public enum CommentSuccessCode implements SuccessCode {

    COMMENT_CREATED(HttpStatus.CREATED, "댓글이 성공적으로 등록되었습니다.");

    private final HttpStatus status;
    private final String message;
}
