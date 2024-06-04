package com.riwi.filtro.infrastructure.services;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.riwi.filtro.api.dto.request.OptionQuestionRequest;
import com.riwi.filtro.api.dto.request.QuestionRequest;
import com.riwi.filtro.api.dto.response.OptionQuestionResponse;
import com.riwi.filtro.api.dto.response.QuestionResponse;
import com.riwi.filtro.domain.entities.OptionQuestion;
import com.riwi.filtro.domain.entities.Question;
import com.riwi.filtro.domain.repositories.OptionQuestionRepository;
import com.riwi.filtro.domain.repositories.QuestionRepository;
import com.riwi.filtro.domain.repositories.SurveyRepository;
import com.riwi.filtro.infrastructure.abstracts.IQuestionService;
import com.riwi.filtro.utils.enums.QuestionType;
import com.riwi.filtro.utils.exceptions.IdNotFoundException;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class QuestionService implements IQuestionService {

  @Autowired
  private final QuestionRepository questionRepository;

  @Autowired
  private final SurveyRepository surveyRepository;

  @Autowired
  private final OptionQuestionRepository optionQuestionRepository;

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

    question = this.questionRepository.save(question);

    if (request.getOptions() != null) {
      for (OptionQuestionRequest optionQuestionRequest : request.getOptions()) {
        OptionQuestion optionQuestion = new OptionQuestion();
        BeanUtils.copyProperties(optionQuestionRequest, optionQuestion);
        optionQuestion.setQuestion(question);
        this.optionQuestionRepository.save(optionQuestion);
      }
    }

    return this.questionToQuestionResponse(question);
  }

  @Override
  public QuestionResponse update(Long id, QuestionRequest request) {
    Question question = findQuestion(id);

    this.questionRequestToQuestion(request, question);

    question = this.questionRepository.save(question);

    if (request.getOptions() != null) {
      for (OptionQuestion optionQuestion : question.getOptionQuestions()) {

        BeanUtils.copyProperties(request.getOptions(), optionQuestion);
        optionQuestion.setQuestion(question);
        this.optionQuestionRepository.save(optionQuestion);
      }
    }

    return this.questionToQuestionResponse(question);
  }

  public QuestionResponse updateWithoutOptions(Long id, QuestionRequest questionRequest) {
    Question question = findQuestion(id);
    question.setActive(questionRequest.isActive());
    question.setSurvey(this.surveyRepository.findById(questionRequest.getSurveyId())
        .orElseThrow(() -> new IdNotFoundException("Surveys")));
    question.setText(questionRequest.getText());
    question.setType(questionRequest.getType());

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

    if (question.getOptionQuestions() != null) {
      questionResponse.setOptions(question.getOptionQuestions().stream().map(this::optionToOptionResponse).toList());
    }

    return questionResponse;
  }

  private Question questionRequestToQuestion(QuestionRequest questionRequest, Question question) {
    BeanUtils.copyProperties(questionRequest, question);
    question.setSurvey(this.surveyRepository.findById(questionRequest.getSurveyId())
        .orElseThrow(() -> new IdNotFoundException("surveys")));

    if (question.getType() == QuestionType.CLOSED) {
      question.setOptionQuestions(questionRequest.getOptions().stream().map(this::optionRequestToOption).toList());
    } else if (question.getType() == QuestionType.OPEN) {
      question.setOptionQuestions(null);
    }
    return question;
  }

  private OptionQuestion optionRequestToOption(OptionQuestionRequest optionQuestionRequest) {
    OptionQuestion optionQuestion = new OptionQuestion();
    BeanUtils.copyProperties(optionQuestionRequest, optionQuestion);

    return optionQuestion;
  }

  private OptionQuestionResponse optionToOptionResponse(OptionQuestion optionQuestion) {
    OptionQuestionResponse optionQuestionResponse = new OptionQuestionResponse();
    BeanUtils.copyProperties(optionQuestion, optionQuestionResponse);
    return optionQuestionResponse;
  }
}
