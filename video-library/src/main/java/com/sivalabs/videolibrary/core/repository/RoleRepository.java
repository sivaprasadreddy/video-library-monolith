package com.sivalabs.videolibrary.core.repository;

import com.sivalabs.videolibrary.core.entity.Role;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByName(String name);
}
