package com.riwi.filtro.domain.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.riwi.filtro.domain.entities.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

}
