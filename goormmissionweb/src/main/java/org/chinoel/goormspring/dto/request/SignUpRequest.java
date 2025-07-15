package org.chinoel.goormspring.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class SignUpRequest {
    @NotBlank(message = "사용자명이 입력되지 않았습니다")
    private String username;

    @NotBlank(message = "비밀번호가 입력되지 않았습니다")
    private String password;

    @NotBlank(message = "이메일이 입력되지 않았습니다")
    @Email(message = "이메일 형식이 올바르지 않습니다")
    private String email;

    private String nickname;
}
