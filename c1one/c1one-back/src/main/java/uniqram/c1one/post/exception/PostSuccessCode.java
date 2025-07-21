package uniqram.c1one.post.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import uniqram.c1one.global.success.SuccessCode;

@Getter
@RequiredArgsConstructor
public enum PostSuccessCode implements SuccessCode {
    POST_CREATED(HttpStatus.CREATED, "게시글이 성공적으로 등록되었습니다.");

    private final HttpStatus status;
    private final String message;
}
