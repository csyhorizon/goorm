package uniqram.c1one.post.dto;

import lombok.Builder;
import lombok.Getter;
import uniqram.c1one.post.entity.Post;

import java.util.List;

@Getter
@Builder
public class HomePostResponse {

    private Long postId;
    private String content;
    private String location;

    private List<String> mediaUrls;

    private Long memberId;
    private String nickname;

    private int likeCount;
    private int commentCount;

    public static HomePostResponse from(Post post, List<String> mediaUrl, int likeCount) {
        return HomePostResponse.builder()
                .postId(post.getId())
                .content(post.getContent())
                .location(post.getLocation())
                .mediaUrls(mediaUrl)
                .likeCount(likeCount)
                .commentCount(post.getCommentCount())
                .memberId(post.getUser().getId())
                .nickname(post.getUser().getUsername())
                .build();
    }
}
