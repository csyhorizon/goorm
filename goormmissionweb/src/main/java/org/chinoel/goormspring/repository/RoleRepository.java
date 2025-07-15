package org.chinoel.goormspring.repository;

import java.util.Optional;
import org.chinoel.goormspring.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> { }
