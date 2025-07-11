package uniqram.c1one.blockeduser.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import uniqram.c1one.blockeduser.entity.BlockedUser;

public interface BlockRepository extends JpaRepository<BlockedUser, Long> {
    Optional<BlockedUser> findByBlockerUserIdAndBlockedUserId(Long blockerUserId, Long blockedUserId);

    List<BlockedUser> findByBlockerUserId(Long blockerUserId);

    void deleteByBlockerUserIdAndBlockedUserId(Long blockerUserId, Long blockedUserId);
}