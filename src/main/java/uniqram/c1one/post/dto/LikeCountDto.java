package uniqram.c1one.post.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LikeCountDto {

    private Long postId;
    private Long count;
}
