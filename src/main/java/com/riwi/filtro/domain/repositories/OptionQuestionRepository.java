package com.riwi.filtro.domain.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.riwi.filtro.domain.persistence.OptionQuestionEntity;

@Repository
public interface OptionQuestionRepository extends JpaRepository<OptionQuestionEntity, Long> {

}
