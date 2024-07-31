package com.riwi.filtro.infrastructure.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.riwi.filtro.api.dto.request.OptionQuestionRequest;
import com.riwi.filtro.api.dto.request.QuestionRequest;
import com.riwi.filtro.api.dto.request.update.QuestionUpdateRequest;
import com.riwi.filtro.api.dto.response.OptionQuestionResponse;
import com.riwi.filtro.api.dto.response.QuestionResponse;
import com.riwi.filtro.domain.entities.OptionQuestion;
import com.riwi.filtro.domain.entities.Question;
import com.riwi.filtro.domain.persistence.OptionQuestionEntity;
import com.riwi.filtro.domain.persistence.QuestionEntity;

@Mapper(componentModel = "spring")
public interface QuestionMapper {

  QuestionResponse questionToResponse(Question question);

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "survey", ignore = true)
  Question requestToQuestion(QuestionRequest questionRequest);

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "survey", ignore = true)
  Question updateToQuestion(QuestionUpdateRequest questionUpdateRequest);

  @Mapping(target = "survey.user.authorities", ignore = true)
  Question entityToQuestion(QuestionEntity questionEntity);

  QuestionEntity questionToEntity(Question question);

  OptionQuestionResponse optionToResponse(OptionQuestion optionQuestion);

  @Mapping(target = "question", ignore = true)
  @Mapping(target = "id", ignore = true)
  OptionQuestion requestToOptionQuestion(OptionQuestionRequest optionQuestionRequest);

  OptionQuestionEntity optionToEntity(OptionQuestion optionQuestion);

  OptionQuestion entityToOption(OptionQuestionEntity optionQuestionEntity);
}
