package uniqram.c1one.profile.repository;

import io.lettuce.core.dynamic.annotation.Param;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uniqram.c1one.profile.entity.Profile;

public interface ProfileRepository extends JpaRepository<Profile, Long> {
    Optional<Profile> findByUserId_Id(Long userId);

    @Query("SELECT p FROM Profile p JOIN FETCH p.userId u WHERE u.id = :userId")
    Optional<Profile> findProfileWithUserByUserId(@Param("userId") Long userId);
}