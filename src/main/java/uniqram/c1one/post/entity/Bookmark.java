package uniqram.c1one.post.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "bookmarks")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Bookmark {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    private Bookmark (Long userId, Post post) {
        this.userId = userId;
        this.post = post;
    }

    public static Bookmark of(Long userId, Post post) {
        return new Bookmark(userId, post);
    }
}
