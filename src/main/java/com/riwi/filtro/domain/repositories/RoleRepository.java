package com.riwi.filtro.domain.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.riwi.filtro.domain.entities.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
  Role findByName(String name);
}
