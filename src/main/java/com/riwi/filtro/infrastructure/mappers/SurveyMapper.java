package com.riwi.filtro.infrastructure.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.riwi.filtro.api.dto.request.SurveyRequest;
import com.riwi.filtro.api.dto.response.SurveyResponse;
import com.riwi.filtro.api.dto.response.SurveyToUser;
import com.riwi.filtro.domain.entities.Survey;

@Mapper(componentModel = "spring")
public interface SurveyMapper {

  SurveyResponse surveyToResponse(Survey survey);

  @Mapping(target = "active", ignore = true)
  @Mapping(target = "creationDate", ignore = true)
  @Mapping(target = "id", ignore = true)
  @Mapping(target = "questions", ignore = true)
  @Mapping(target = "user", ignore = true)
  Survey requestToSurvey(SurveyRequest surveyRequest);

  SurveyToUser surveyToSurveyToUser(Survey survey);

}
