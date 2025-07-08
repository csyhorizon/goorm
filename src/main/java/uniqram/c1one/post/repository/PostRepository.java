package uniqram.c1one.post.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import uniqram.c1one.post.entity.Post;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    // 전체 게시글을 최신순 조회(홈화면용)
    Page<Post> findAllByOrderByIdDesc(Pageable pageable);

    // 특정 사용자의 게시글 리스트
    Page<Post> findByUserIdOrderByIdDesc(Long userId, Pageable pageable);

    @Query("SELECT p FROM Post p WHERE p.user.id IN :userIds AND p.createdAt >= :after ORDER BY p.id DESC")
    List<Post> findRecentPostsByUserIds(@Param("userIds") List<Long> userIds,
                                        @Param("after") LocalDateTime after,
                                        Pageable pageable);

    @Query("SELECT p FROM Post p WHERE p.user.id NOT IN :followingIds ORDER BY FUNCTION('RAND')")
    Page<Post> findRecommendedPosts(@Param("followingIds") List<Long> followingIds, Pageable pageable);

    // 좋아요 순 상위 N개
    @Query("SELECT p FROM Post p WHERE p.user.id NOT IN :excludeUserIds ORDER BY p.likeCount DESC")
    List<Post> findTopLikedPosts(@Param("excludeUserIds") List<Long> excludeUserIds, Pageable pageable);

    @Query("SELECT p FROM Post p WHERE p.user.id NOT IN :excludeUserIds AND p.id NOT IN :excludePostIds ORDER BY function('RAND') ")
    List<Post> findRandomPosts(@Param("excludeUserIds") List<Long> excludeUserIds,
                               @Param("excludePostIds") List<Long> excludePostIds,
                               Pageable pageable);
}
