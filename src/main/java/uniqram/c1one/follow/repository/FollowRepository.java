package uniqram.c1one.follow.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import uniqram.c1one.follow.entity.Follow;
import uniqram.c1one.user.entity.Users;

public interface FollowRepository extends JpaRepository<Follow, Long> {
    List<Follow> findByFollower_Id(Long userId);
    List<Follow> findByFollowing_Id(Long followingId);

    Optional<Follow> findByFollowerAndFollowing(Users follower, Users following);
}
