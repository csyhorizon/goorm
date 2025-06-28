package uniqram.c1one.post.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class CommentDto {

    private Long postId;
    private Long commentId;
    private String username;
    private String content;
    private LocalDateTime createdAt;
}
