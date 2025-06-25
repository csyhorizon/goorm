package uniqram.c1one.post.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import uniqram.c1one.global.success.SuccessCode;

@Getter
@RequiredArgsConstructor
public enum BookmarkSuccessCode implements SuccessCode {
    BOOKMARK_TOGGLED(HttpStatus.CREATED, "북마크 상태가 변경되었습니다.");

    private final HttpStatus status;
    private final String message;
}
