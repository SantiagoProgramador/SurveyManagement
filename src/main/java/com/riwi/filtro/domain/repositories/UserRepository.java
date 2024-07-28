package com.riwi.filtro.domain.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.riwi.filtro.domain.persistence.UserEntity;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
  UserEntity findByUsername(String username);
}
