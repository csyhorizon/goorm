package uniqram.c1one.post.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PostResponse {

    private Long postId;
    private String content;
    private String location;
}
