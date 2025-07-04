package uniqram.c1one.post.dto;

import lombok.Builder;
import lombok.Getter;
import uniqram.c1one.comment.dto.CommentResponse;
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
    private String username;

    private int likeCount;
    private List<LikeUserDto> likeUsers;
    private boolean likedByMe;

    private int commentCount;
    private List<CommentResponse> comments;

    public static HomePostResponse from(Post post,
                                        List<String> mediaUrl,
                                        int likeCount,
                                        List<LikeUserDto> likeUsers,
                                        boolean likedByMe,
                                        List<CommentResponse> comments) {
        return HomePostResponse.builder()
                .postId(post.getId())
                .content(post.getContent())
                .location(post.getLocation())
                .mediaUrls(mediaUrl)
                .likeCount(likeCount)
                .likeUsers(likeUsers)
                .likedByMe(likedByMe)
//                .commentCount(commentCount)
                .comments(comments)
                .memberId(post.getUser().getId())
                .username(post.getUser().getUsername())
                .build();
    }
}
