package uniqram.c1one.post.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CommentCountDto {

    private Long postId;
    private Long count;
}
