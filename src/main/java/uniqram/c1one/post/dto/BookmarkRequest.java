package uniqram.c1one.post.dto;

import lombok.*;

@Getter
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BookmarkRequest {

    private Long postId;
}
