package com.indus.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.indus.entity.ERole;
import com.indus.entity.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
  Optional<Role> findByName(ERole name);
}
