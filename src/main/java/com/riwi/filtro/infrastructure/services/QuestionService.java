package com.riwi.filtro.infrastructure.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.riwi.filtro.api.dto.request.OptionQuestionRequest;
import com.riwi.filtro.api.dto.request.QuestionRequest;
import com.riwi.filtro.api.dto.request.update.QuestionUpdateRequest;
import com.riwi.filtro.api.dto.response.QuestionResponse;
import com.riwi.filtro.domain.entities.OptionQuestion;
import com.riwi.filtro.domain.entities.Question;
import com.riwi.filtro.domain.entities.Survey;
import com.riwi.filtro.domain.repositories.OptionQuestionRepository;
import com.riwi.filtro.domain.repositories.QuestionRepository;
import com.riwi.filtro.infrastructure.abstracts.IQuestionService;
import com.riwi.filtro.infrastructure.abstracts.ISurveyService;
import com.riwi.filtro.infrastructure.mappers.QuestionMapper;
import com.riwi.filtro.utils.enums.QuestionType;
import com.riwi.filtro.utils.exceptions.IdNotFoundException;
import com.riwi.filtro.utils.exceptions.QuestionTypeException;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class QuestionService implements IQuestionService {

  @Autowired
  private final QuestionRepository questionRepository;

  @Autowired
  private final OptionQuestionRepository optionQuestionRepository;

  @Autowired
  private final QuestionMapper questionMapper;

  @Autowired
  private final ISurveyService surveyService;

  @Override
  public Page<QuestionResponse> getAll(int size, int page) {
    if (page < 0) {
      page = 0;
    }
    Pageable pageable = PageRequest.of(page, size);

    return this.questionRepository.findAll(pageable).map(questionMapper::questionToResponse);
  }

  @Override
  public QuestionResponse getById(Long id) {
    Question question = findEntity(id);

    return this.questionMapper.questionToResponse(question);
  }

  @Override
  public Question findEntity(Long id) {
    return this.questionRepository.findById(id).orElseThrow(() -> new IdNotFoundException("questions"));
  }

  @Override
  public QuestionResponse create(QuestionRequest request) {
    Survey survey = this.surveyService.findEntity(request.getSurveyId());

    if (!this.isTypeCorrect(request)) {
      throw new QuestionTypeException();
    }

    Question question = this.questionMapper.requestToQuestion(request);
    question.setSurvey(survey);

    if (request.getOptions() != null) {
      for (OptionQuestionRequest optionQuestionRequest : request.getOptions()) {
        OptionQuestion optionQuestion = this.questionMapper.requestToOptionQuestion(optionQuestionRequest);
        optionQuestion.setQuestion(question);
        this.optionQuestionRepository.save(optionQuestion);
      }
    }

    return this.questionMapper.questionToResponse(this.questionRepository.save(question));
  }

  @Override
  public QuestionResponse update(Long id, QuestionUpdateRequest request) {
    Question question = findEntity(id);

    if (question.getSurvey().getId().equals(request.getSurveyId())) {
      Survey survey = this.surveyService.findEntity(id);
      question.setSurvey(survey);
    }
    if (!this.isTypeCorrect(request)) {
      throw new QuestionTypeException();
    }

    question = this.questionMapper.updateToQuestion(request);

    if (request.getOptions() != null) {
      this.optionQuestionRepository.deleteAll(question.getOptions());
      for (OptionQuestionRequest optionQuestionRequest : request.getOptions()) {
        OptionQuestion optionQuestion = this.questionMapper.requestToOptionQuestion(optionQuestionRequest);
        optionQuestion.setQuestion(question);
        this.optionQuestionRepository.save(optionQuestion);
      }
    }
    return this.questionMapper.questionToResponse(this.questionRepository.save(question));
  }

  @Override
  public QuestionResponse updateWithoutOptions(Long id, QuestionUpdateRequest questionRequest) {
    Question question = findEntity(id);

    if (question.getSurvey().getId().equals(questionRequest.getSurveyId())) {
      Survey survey = this.surveyService.findEntity(id);
      question.setSurvey(survey);
    }
    question = this.questionMapper.updateToQuestion(questionRequest);

    return this.questionMapper.questionToResponse(this.questionRepository.save(question));
  }

  @Override
  public void delete(Long id) {
    Question question = findEntity(id);
    this.questionRepository.delete(question);
  }

  private boolean isTypeCorrect(QuestionRequest questionRequest) {

    return (questionRequest.getType() != QuestionType.CLOSED || questionRequest.getOptions() != null)
        && (questionRequest.getType() != QuestionType.OPEN || questionRequest.getOptions() == null);
  }
}
