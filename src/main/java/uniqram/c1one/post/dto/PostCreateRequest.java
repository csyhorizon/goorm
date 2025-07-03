package uniqram.c1one.post.dto;

import lombok.*;

import java.util.List;

@Getter
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostCreateRequest {

    private String content;
    private String location;
    private List<String> mediaUrls;
}
