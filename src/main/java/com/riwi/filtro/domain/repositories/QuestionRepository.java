package com.riwi.filtro.domain.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.riwi.filtro.domain.persistence.QuestionEntity;

public interface QuestionRepository extends JpaRepository<QuestionEntity, Long> {

}
