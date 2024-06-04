package com.riwi.filtro.infrastructure.services;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.riwi.filtro.api.dto.request.QuestionRequest;
import com.riwi.filtro.api.dto.response.QuestionResponse;
import com.riwi.filtro.domain.entities.Question;
import com.riwi.filtro.domain.repositories.QuestionRepository;
import com.riwi.filtro.domain.repositories.SurveyRepository;
import com.riwi.filtro.infrastructure.abstracts.IQuestionService;
import com.riwi.filtro.utils.exceptions.IdNotFoundException;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class QuestionService implements IQuestionService {

  @Autowired
  private final QuestionRepository questionRepository;

  @Autowired
  private final SurveyRepository surveyRepository;

  @Override
  public Page<QuestionResponse> getAll(int size, int page) {
    if (page < 0) {
      page = 0;
    }
    Pageable pageable = PageRequest.of(page, size);

    return this.questionRepository.findAll(pageable).map(this::questionToQuestionResponse);
  }

  @Override
  public QuestionResponse getById(Long id) {
    Question question = findQuestion(id);

    return this.questionToQuestionResponse(question);
  }

  private Question findQuestion(Long id) {
    return this.questionRepository.findById(id).orElseThrow(() -> new IdNotFoundException("questions"));
  }

  @Override
  public QuestionResponse create(QuestionRequest request) {
    Question question = new Question();
    this.questionRequestToQuestion(request, question);

    return this.questionToQuestionResponse(this.questionRepository.save(question));
  }

  @Override
  public QuestionResponse update(Long id, QuestionRequest request) {
    Question question = findQuestion(id);

    this.questionRequestToQuestion(request, question);

    return this.questionToQuestionResponse(this.questionRepository.save(question));
  }

  @Override
  public void delete(Long id) {
    Question question = findQuestion(id);
    this.questionRepository.delete(question);
  }

  private QuestionResponse questionToQuestionResponse(Question question) {
    QuestionResponse questionResponse = new QuestionResponse();
    BeanUtils.copyProperties(question, questionResponse);

    return questionResponse;
  }

  private Question questionRequestToQuestion(QuestionRequest questionRequest, Question question) {
    BeanUtils.copyProperties(questionRequest, question);
    question.setSurvey(this.surveyRepository.findById(questionRequest.getSurveyId())
        .orElseThrow(() -> new IdNotFoundException("questions")));
    return question;
  }

}
