package uniqram.c1one.post.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LikeUserDto {

    private Long postId;
    private Long userId;
    private String username;
}
