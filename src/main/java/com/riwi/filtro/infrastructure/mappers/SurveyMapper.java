package com.riwi.filtro.infrastructure.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.riwi.filtro.api.dto.request.SurveyRequest;
import com.riwi.filtro.api.dto.request.update.SurveyUpdateRequest;
import com.riwi.filtro.api.dto.response.SurveyResponse;
import com.riwi.filtro.api.dto.response.SurveyToUser;
import com.riwi.filtro.domain.entities.Survey;
import com.riwi.filtro.domain.persistence.SurveyEntity;

@Mapper(componentModel = "spring")
public interface SurveyMapper {

  SurveyResponse surveyToResponse(Survey survey);

  @Mapping(target = "active", ignore = true)
  @Mapping(target = "creationDate", ignore = true)
  @Mapping(target = "id", ignore = true)
  @Mapping(target = "questions", ignore = true)
  @Mapping(target = "user", ignore = true)
  Survey requestToSurvey(SurveyRequest surveyRequest);

  @Mapping(target = "active", ignore = true)
  @Mapping(target = "creationDate", ignore = true)
  @Mapping(target = "id", ignore = true)
  @Mapping(target = "questions", ignore = true)
  @Mapping(target = "user", ignore = true)
  Survey updateToSurvey(SurveyUpdateRequest surveyUpdateRequest);

  SurveyToUser surveyToSurveyToUser(Survey survey);

  @Mapping(target = "user.authorities", ignore = true)
  Survey entityToSurvey(SurveyEntity surveyEntity);

  SurveyEntity surveyToEntity(Survey survey);

}
