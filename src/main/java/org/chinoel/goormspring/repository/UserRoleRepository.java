package org.chinoel.goormspring.repository;

import java.util.List;
import org.chinoel.goormspring.entity.User;
import org.chinoel.goormspring.entity.UserRole;
import org.chinoel.goormspring.entity.UserRoleId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, UserRoleId> {
    List<UserRole> findByUser(User user);
}
