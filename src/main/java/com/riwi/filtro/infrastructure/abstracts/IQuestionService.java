package com.riwi.filtro.infrastructure.abstracts;

import com.riwi.filtro.api.dto.request.QuestionRequest;
import com.riwi.filtro.api.dto.response.QuestionResponse;
import com.riwi.filtro.domain.entities.Question;

public interface IQuestionService extends BaseService<Question,QuestionRequest, QuestionResponse, Long> {
  public QuestionResponse updateWithoutOptions(Long id, QuestionRequest questionRequest);

  
}
