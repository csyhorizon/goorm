package uniqram.c1one.profile.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ProfileErrorCode {
    PROFILE_NOT_FOUND(HttpStatus.NOT_FOUND, "프로필을 찾을 수 없습니다."),
    DUPLICATE_PROFILE(HttpStatus.CONFLICT, "이미 프로필이 존재합니다."),
    UNAUTHORIZED_PROFILE_ACCESS(HttpStatus.FORBIDDEN, "프로필에 접근할 권한이 없습니다."),
    PROFILE_UPDATE_NONE_PERMISSION(HttpStatus.FORBIDDEN, "프로필을 수정할 권한이 없습니다."),
    PROFILE_UPDATE_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "프로필 수정에 실패했습니다.")
    ;

    private final HttpStatus status;
    private final String message;
}
