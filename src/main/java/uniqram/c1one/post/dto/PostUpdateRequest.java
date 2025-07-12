package uniqram.c1one.post.dto;

import lombok.*;

import java.util.List;

@Getter
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostUpdateRequest {

    private String content;
    private String location;

    // 삭제 후 남길 이미지
    private List<String> remainImageUrls;
}
