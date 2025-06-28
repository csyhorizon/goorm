package uniqram.c1one.post.dto;

import lombok.Builder;
import lombok.Getter;
import uniqram.c1one.post.entity.Post;

import java.util.List;

@Getter
@Builder
public class PostResponse {

    private Long postId;
    private String content;
    private String location;

    private List<String>  mediaUrls;

    private int likeCount;
    private int commentCount;

    private Long memberId;
    private String username;

    public static PostResponse from(Post post, List<String> mediaUrl) {
        return PostResponse.builder()
                .postId(post.getId())
                .content(post.getContent())
                .location(post.getLocation())
                .mediaUrls(mediaUrl)
                .likeCount(post.getLikeCount())
                .commentCount(post.getCommentCount())
                .memberId(post.getUser().getId())
                .username(post.getUser().getUsername())
                .build();
    }
}
