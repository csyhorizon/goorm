package uniqram.c1one.follow.repository;

import io.lettuce.core.dynamic.annotation.Param;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uniqram.c1one.follow.dto.FollowDto;
import uniqram.c1one.follow.entity.Follow;
import uniqram.c1one.user.entity.Users;

public interface FollowRepository extends JpaRepository<Follow, Long> {
    int deleteByFollowerIdAndFollowingId(Long followerId, Long followingId);

    boolean existsByFollowerAndFollowing(Users follower, Users following);

    @Query("""
        SELECT new uniqram.c1one.follow.dto.FollowDto(
            p.id,
            u.id,
            u.username,
            p.profileImageUrl,
            p.bio
        )
        FROM Follow f
        JOIN f.follower u
        JOIN Profile p ON p.userId = u
        WHERE f.following.id = :userId
    """)
    List<FollowDto> findFollowersByUserId(@Param("userId") Long userId);


    @Query("""
        SELECT new uniqram.c1one.follow.dto.FollowDto(
            p.id,
            u.id,
            u.username,
            p.profileImageUrl,
            p.bio
        )
        FROM Follow f
        JOIN f.following u
        JOIN Profile p ON p.userId = u
        WHERE f.follower.id = :userId
    """)
    List<FollowDto> findFollowingsByUserId(@Param("userId") Long userId);

    @Query("""
    SELECT u.id
    FROM Follow f
    JOIN f.follower u
    WHERE f.following.id = :userId
""")
    List<Long> findFollowerIdsByUserId(@Param("userId") Long userId);
}
