package uniqram.c1one.follow.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.NotNull;
import java.time.Instant;
import lombok.Getter;
import lombok.NoArgsConstructor;
import uniqram.c1one.user.entity.Users;

@Getter
@Entity
@NoArgsConstructor
@Table(
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "UK_follower_following",
                        columnNames = {"follower_id", "following_id"}
                )
        }
)
public class Follow {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "follower_id", nullable = false)
    private Users follower;

    @ManyToOne
    @JoinColumn(name = "following_id", nullable = false)
    private Users following;

    @NotNull
    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    public Follow(Users follower, Users following) {
        this.follower = follower;
        this.following = following;
    }

    @PrePersist
    public void prePersist() {
        this.createdAt = Instant.now();
    }
}
