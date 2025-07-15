package org.chinoel.goormspring.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class PostAddDto {
    @NotBlank(message = "제목이 입력되지 않았습니다")
    private String title;

    @NotBlank(message = "내용이 작성되지 않았습니다")
    private String content;

    private boolean secret;
}