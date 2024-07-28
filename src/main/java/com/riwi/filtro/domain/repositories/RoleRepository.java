package com.riwi.filtro.domain.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.riwi.filtro.domain.persistence.RoleEntity;

public interface RoleRepository extends JpaRepository<RoleEntity, Long> {
  RoleEntity findByName(String name);
}
