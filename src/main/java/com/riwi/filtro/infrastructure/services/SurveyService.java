package com.riwi.filtro.infrastructure.services;

import java.time.LocalDateTime;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.riwi.filtro.api.dto.request.SurveyRequest;
import com.riwi.filtro.api.dto.response.OptionQuestionResponse;
import com.riwi.filtro.api.dto.response.QuestionResponse;
import com.riwi.filtro.api.dto.response.SurveyResponse;
import com.riwi.filtro.api.dto.response.UserToSurvey;
import com.riwi.filtro.domain.entities.OptionQuestion;
import com.riwi.filtro.domain.entities.Question;
import com.riwi.filtro.domain.entities.Survey;
import com.riwi.filtro.domain.entities.User;
import com.riwi.filtro.domain.repositories.SurveyRepository;
import com.riwi.filtro.domain.repositories.UserRepository;
import com.riwi.filtro.infrastructure.abstracts.ISurveyService;
import com.riwi.filtro.infrastructure.abstracts.IUserService;
import com.riwi.filtro.infrastructure.mappers.SurveyMapper;
import com.riwi.filtro.utils.exceptions.IdNotFoundException;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class SurveyService implements ISurveyService {

  @Autowired
  private final SurveyRepository surveyRepository;
  @Autowired
  private final UserRepository userRepository;

  @Autowired
  private final SurveyMapper surveyMapper;

  @Autowired
  private final IUserService userService;

  @Override
  public Page<SurveyResponse> getAll(int size, int page) {
    if (page < 0) {
      page = 0;
    }
    Pageable pageable = PageRequest.of(page, size);

    return this.surveyRepository.findAll(pageable).map(this::surveyToSurveyResponse);
  }

  @Override
  public SurveyResponse getById(Long id) {
    Survey survey = findEntity(id);
    SurveyResponse surveyResponse = new SurveyResponse();

    BeanUtils.copyProperties(survey, surveyResponse);
    if (survey.getQuestions() != null) {
      surveyResponse.setQuestions(survey.getQuestions().stream().map(this::questionToQuestionResponse).toList());
    }
    surveyResponse.setUser(this.userToUserToSurvey(survey.getUser()));
    return surveyResponse;
  }

  @Override
  public Survey findEntity(Long id) {
    return this.surveyRepository.findById(id).orElseThrow(() -> new IdNotFoundException("surveys"));
  }

  @Override
  public SurveyResponse create(SurveyRequest request) {
    Survey survey = new Survey();

    this.surveyRequestToSurvey(request, survey);

    return this.surveyToSurveyResponse(this.surveyRepository.save(survey));
  }

  @Override
  public SurveyResponse update(Long id, SurveyRequest request) {
    Survey survey = findEntity(id);
    this.surveyRequestToSurvey(request, survey);
    return this.surveyToSurveyResponse(this.surveyRepository.save(survey));
  }

  @Override
  public void delete(Long id) {
    Survey survey = findEntity(id);
    this.surveyRepository.delete(survey);
  }

  private SurveyResponse surveyToSurveyResponse(Survey survey) {
    SurveyResponse surveyResponse = new SurveyResponse();
    BeanUtils.copyProperties(survey, surveyResponse);
    surveyResponse.setUser(this.userToUserToSurvey(survey.getUser()));

    return surveyResponse;
  }

  private Survey surveyRequestToSurvey(SurveyRequest surveyRequest, Survey survey) {
    BeanUtils.copyProperties(surveyRequest, survey);
    survey.setUser(
        this.userRepository.findById(surveyRequest.getUserId()).orElseThrow(() -> new IdNotFoundException("users")));
    survey.setCreationDate(LocalDateTime.now());
    survey.setActive(true);
    return survey;
  }

  private UserToSurvey userToUserToSurvey(User user) {
    UserToSurvey userToSurvey = new UserToSurvey();
    BeanUtils.copyProperties(user, userToSurvey);

    return userToSurvey;
  }

  private QuestionResponse questionToQuestionResponse(Question question) {
    QuestionResponse questionResponse = new QuestionResponse();
    BeanUtils.copyProperties(question, questionResponse);
    questionResponse.setOptions(question.getOptionQuestions().stream().map(this::optionToOptionResponse).toList());
    return questionResponse;
  }

  private OptionQuestionResponse optionToOptionResponse(OptionQuestion optionQuestion) {
    OptionQuestionResponse optionQuestionResponse = new OptionQuestionResponse();
    BeanUtils.copyProperties(optionQuestion, optionQuestionResponse);
    return optionQuestionResponse;
  }
}
