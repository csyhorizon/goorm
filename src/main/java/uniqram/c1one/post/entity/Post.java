package uniqram.c1one.post.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import uniqram.c1one.comment.entity.Comment;
import uniqram.c1one.global.BaseEntity;
import uniqram.c1one.user.entity.Users;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "posts")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private Users user;

    @Column(length = 4096)
    private String content;

    private String location;

    private int likeCount;
    private int commentCount;

    // 고급 설정
    private Boolean hideLikeAndViewCount = false;
    private Boolean disableComments = false;

    private Post(Users user, String content, String location) {
        this.user = user;
        this.content = content;
        this.location = location;
    }

    public static Post of(Users user, String content, String location) {
        return new Post(user, content, location);
    }

    @OneToMany(mappedBy = "post", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();

    public void increaseLikesCount() {
        this.likeCount = likeCount + 1;
    }
    public void decreaseLikesCount() {
        this.likeCount = likeCount - 1;
    }

    public void update(String content, String location) {
        this.content = content;
        this.location = location;
    }
}
