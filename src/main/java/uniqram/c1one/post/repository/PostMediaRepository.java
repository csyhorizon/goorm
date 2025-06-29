package uniqram.c1one.post.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import uniqram.c1one.post.entity.PostMedia;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostMediaRepository extends JpaRepository<PostMedia, Long> {

    // 특정 게시물 1개의 첫번째 이미지
    Optional<PostMedia> findFirstByPostIdOrderByIdAsc(@Param("postId") Long postId);

    // 특정 게시물의 전체 이미지
    List<PostMedia> findByPostIdOrderByIdAsc(Long postId);

    // 여러 게시물의 전체 이미지
    List<PostMedia> findByPostIdIn(List<Long> postIds);

    // 여러 게시물의 첫번째 이미지
    @Query(value = "SELECT pm.* " +
            "FROM post_media pm " +
            "INNER JOIN ( " +
            "   SELECT post_id, MIN(id) AS min_id " +
            "   FROM post_media " +
            "   WHERE post_id IN :postIds " +
            "   GROUP BY post_id " +
            ") first_pm ON pm.id = first_pm.min_id", nativeQuery = true)
    List<PostMedia> findFirstImagesByPostIds(@Param("postIds") List<Long> postIds);
}
