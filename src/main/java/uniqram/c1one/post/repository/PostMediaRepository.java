package uniqram.c1one.post.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import uniqram.c1one.post.entity.PostMedia;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostMediaRepository extends JpaRepository<PostMedia, Long> {

    Optional<PostMedia> findFirstByPostIdOrderByIdAsc(@Param("postId") Long postId);

    List<PostMedia> findByPostIdOrderByIdAsc(Long postId);

    List<PostMedia> findByPostIdIn(List<Long> postIds);
}
