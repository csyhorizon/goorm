package uniqram.c1one.profile.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import uniqram.c1one.profile.entity.Profile;

public interface ProfileRepository extends JpaRepository<Profile, Long> {
    Optional<Profile> findByUserId_Id(Long userId);
}