package com.apside.prono.securityconfig.repository;

import com.apside.prono.securityconfig.model.Role;
import com.apside.prono.securityconfig.model.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(RoleName roleName);
}