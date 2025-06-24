package uniqram.c1one.post.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.*;

import java.util.List;

@Getter
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostRequest {

    private Long userId;   // 임시 포함
    private String content;
    private String location;

    @NotEmpty
    private List<String> mediaUrls;
}
