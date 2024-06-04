package com.riwi.filtro.domain.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.riwi.filtro.domain.entities.Question;

public interface QuestionRepository extends JpaRepository<Question,Long> {
  
}
