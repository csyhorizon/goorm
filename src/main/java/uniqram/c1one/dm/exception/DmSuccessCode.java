package uniqram.c1one.dm.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import uniqram.c1one.global.success.SuccessCode;

@Getter
@RequiredArgsConstructor
public enum DmSuccessCode implements SuccessCode {

    MESSAGE_CREATED(HttpStatus.CREATED, "메세지가 성공적으로 등록되었습니다.");

    private final HttpStatus status;
    private final String message;
}
