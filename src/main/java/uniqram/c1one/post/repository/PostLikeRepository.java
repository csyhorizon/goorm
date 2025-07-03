package uniqram.c1one.post.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import uniqram.c1one.post.dto.LikeCountDto;
import uniqram.c1one.post.dto.LikeUserDto;
import uniqram.c1one.post.entity.Post;
import uniqram.c1one.post.entity.PostLike;
import uniqram.c1one.user.entity.Users;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostLikeRepository extends JpaRepository<PostLike, Long> {
    int countByPost(Post post);
    boolean existsByUserAndPost(Users user, Post post);
    Optional<PostLike> findByUserAndPost(Users user, Post post);
    int countByPostId(Long postId);

    @Query("SELECT new uniqram.c1one.post.dto.LikeCountDto(pl.post.id, COUNT(pl)) " +
            "FROM PostLike pl WHERE pl.post.id IN :postIds GROUP BY pl.post.id")
    List<LikeCountDto> countByPostIds(@Param("postIds") List<Long> postIds);

    @Query("SELECT new uniqram.c1one.post.dto.LikeUserDto(pl.post.id, pl.user.id, pl.user.username) " +
            "FROM PostLike pl WHERE pl.post.id IN :postIds")
    List<LikeUserDto> findLikeUsersByPostIds(@Param("postIds") List<Long> postIds);

    @Query("SELECT new uniqram.c1one.post.dto.LikeUserDto(pl.post.id, pl.user.id, pl.user.username) " +
            "FROM PostLike pl WHERE pl.post.id = :postId")
    List<LikeUserDto> findLikeUsersByPostId(@Param("postId") Long postId);

    @Query("SELECT pl.post.id FROM PostLike pl WHERE pl.post.id IN :postIds AND pl.user.id = :userId")
    List<Long> findPostIdsLikedByUser(@Param("postIds") List<Long> postIds, @Param("userId") Long userId);

    boolean existsByPostIdAndUserId(Long postId, Long userId);
}
