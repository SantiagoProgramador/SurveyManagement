package com.riwi.filtro.infrastructure.abstracts;

import com.riwi.filtro.api.dto.request.SurveyRequest;
import com.riwi.filtro.api.dto.response.SurveyResponse;
import com.riwi.filtro.domain.entities.Survey;

public interface ISurveyService extends BaseService<Survey, SurveyRequest, SurveyResponse, Long> {

}
