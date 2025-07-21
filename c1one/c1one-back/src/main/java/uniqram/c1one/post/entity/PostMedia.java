package uniqram.c1one.post.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "post_media")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostMedia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_media_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    @Column(length = 1024, nullable = false)
    private String mediaUrl;

    private PostMedia(Post post, String mediaUrl) {
        this.post = post;
        this.mediaUrl = mediaUrl;
    }

    public static PostMedia of(Post post, String mediaUrl) {
        return new PostMedia(post, mediaUrl);
    }
}
