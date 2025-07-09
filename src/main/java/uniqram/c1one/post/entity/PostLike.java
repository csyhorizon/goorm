package uniqram.c1one.post.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import uniqram.c1one.global.BaseEntity;
import uniqram.c1one.user.entity.Users;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "postLikes", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"user_id", "post_id"})
})
public class PostLike extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_like_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id")
    private Users user;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "post_id")
    private Post post;
}
