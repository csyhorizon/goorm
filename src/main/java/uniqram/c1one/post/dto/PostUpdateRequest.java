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

    // 기존 이미지
    private List<String> remainImageUrls;

    // 새로 추가할 이미지
    private List<String> newImageUrls;
}
