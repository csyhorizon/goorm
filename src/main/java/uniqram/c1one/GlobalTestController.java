package uniqram.c1one;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uniqram.c1one.global.exception.BaseException;
import uniqram.c1one.global.exception.GlobalErrorCode;
import uniqram.c1one.global.success.GlobalSuccessCode;
import uniqram.c1one.global.success.SuccessResponse;

@RestController
@RequestMapping("/api/users")
public class GlobalTestController {

    @GetMapping("/test/success")
    public ResponseEntity<SuccessResponse<String>> successTest() {
        return ResponseEntity.ok(SuccessResponse.of(GlobalSuccessCode.OK, "성공 응답 테스트"));
    }

    @GetMapping("/test/fail")
    public ResponseEntity<?> failTest() {
        throw new BaseException(GlobalErrorCode.RESOURCE_NOT_FOUND) {};
    }
}
