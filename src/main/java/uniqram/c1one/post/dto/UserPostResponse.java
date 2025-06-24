package uniqram.c1one.post.dto;

import lombok.Builder;
import lombok.Getter;
import uniqram.c1one.post.entity.Post;

@Getter
@Builder
public class UserPostResponse {

    private Long postId;
    private String representativeImageUrl;

    public static UserPostResponse from(Post post, String repImageUrl) {
        return UserPostResponse.builder()
                .postId(post.getId())
                .representativeImageUrl(repImageUrl)
                .build();
    }
}
