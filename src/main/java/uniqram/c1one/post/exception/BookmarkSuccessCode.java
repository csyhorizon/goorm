package uniqram.c1one.post.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import uniqram.c1one.global.success.SuccessCode;

@Getter
@RequiredArgsConstructor
public enum BookmarkSuccessCode implements SuccessCode {
    BOOKMARK_TOGGLED(HttpStatus.CREATED, "북마크 상태가 변경되었습니다."),
    BOOKMARK_LIST_LOADED(HttpStatus.OK, "북마크한 게시물을 성공적으로 조회했습니다.");

    private final HttpStatus status;
    private final String message;
}
