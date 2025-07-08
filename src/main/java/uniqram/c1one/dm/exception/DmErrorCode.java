package uniqram.c1one.dm.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import uniqram.c1one.global.exception.ErrorCode;

@Getter
@RequiredArgsConstructor
public enum DmErrorCode implements ErrorCode {

    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "사용자를 찾을 수 없습니다."),
    CONTENT_REQUIRED(HttpStatus.BAD_REQUEST, "메세지 내용은 필수 입력 값입니다."),
    MESSAGE_NOT_FOUND(HttpStatus.NOT_FOUND, "매세지를 찾을 수 없습니다."),
    CHATROOM_NOT_FOUND(HttpStatus.NOT_FOUND, "채팅방을 찾을 수 없습니다."),
    NO_AUTHORITY(HttpStatus.FORBIDDEN, "수정 또는 삭제 권한이 없습니다.");

    private final HttpStatus status;
    private final String message;


}
