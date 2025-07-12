package uniqram.c1one.post.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import uniqram.c1one.post.entity.Bookmark;

import java.util.List;

@Repository
public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {

    boolean existsByUserIdAndPostId(Long userId, Long postId);

    void deleteByUserIdAndPostId(Long userId, Long postId);

    List<Bookmark> findByUserId(Long userId);

    @Query("SELECT b.post.id FROM Bookmark b WHERE b.userId = :userId AND b.post.id IN :postIds")
    List<Long> findPostIdsBookmarkedByUser(@Param("postIds") List<Long> postIds, @Param("userId") Long userId);
}
