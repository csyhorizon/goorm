package uniqram.c1one.post.dto;

import lombok.Builder;
import lombok.Getter;
import uniqram.c1one.post.entity.Post;

import java.util.List;

@Getter
@Builder
public class PostDetailResponse {

    private Long postId;
    private String content;
    private String location;
    private List<String> mediaUrls;
    private Long memberId;
    private String username;

    private int likeCount;
    private List<LikeUserDto> likeUsers;
    private boolean likedByMe;

    private int commentCount;
    private List<CommentDto> comments;

    public static PostDetailResponse from(Post post,
                                        List<String> mediaUrl,
                                        int likeCount,
                                        List<LikeUserDto> likeUsers,
                                        boolean likedByMe) {
        return PostDetailResponse.builder()
                .postId(post.getId())
                .content(post.getContent())
                .location(post.getLocation())
                .mediaUrls(mediaUrl)
                .likeCount(likeCount)
                .likeUsers(likeUsers)
                .likedByMe(likedByMe)
//                .commentCount(commentCount)
//                .comments(comments)
                .memberId(post.getUser().getId())
                .username(post.getUser().getUsername())
                .build();
    }
}
