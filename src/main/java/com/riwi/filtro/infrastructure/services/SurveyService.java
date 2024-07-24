package com.riwi.filtro.infrastructure.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.riwi.filtro.api.dto.request.SurveyRequest;
import com.riwi.filtro.api.dto.response.SurveyResponse;
import com.riwi.filtro.domain.entities.Survey;
import com.riwi.filtro.domain.entities.User;
import com.riwi.filtro.domain.repositories.SurveyRepository;
import com.riwi.filtro.infrastructure.abstracts.ISurveyService;
import com.riwi.filtro.infrastructure.abstracts.IUserService;
import com.riwi.filtro.infrastructure.mappers.QuestionMapper;
import com.riwi.filtro.infrastructure.mappers.SurveyMapper;
import com.riwi.filtro.infrastructure.mappers.UserMapper;
import com.riwi.filtro.utils.exceptions.IdNotFoundException;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class SurveyService implements ISurveyService {

  @Autowired
  private final SurveyRepository surveyRepository;

  @Autowired
  private final SurveyMapper surveyMapper;

  @Autowired
  private final QuestionMapper questionMapper;

  @Autowired
  private final IUserService userService;

  @Autowired
  private final UserMapper userMapper;

  @Override
  public Page<SurveyResponse> getAll(int size, int page) {
    if (page < 0) {
      page = 0;
    }
    Pageable pageable = PageRequest.of(page, size);

    return this.surveyRepository.findAll(pageable).map(surveyMapper::surveyToResponse);
  }

  @Override
  public SurveyResponse getById(Long id) {
    Survey survey = findEntity(id);
    SurveyResponse surveyResponse = this.surveyMapper.surveyToResponse(survey);

    if (survey.getQuestions() != null) {
      surveyResponse.setQuestions(survey.getQuestions().stream().map(questionMapper::questionToResponse).toList());
    }
    surveyResponse.setUser(this.userMapper.userToUserToSurvey(survey.getUser()));
    return surveyResponse;
  }

  @Override
  public Survey findEntity(Long id) {
    return this.surveyRepository.findById(id).orElseThrow(() -> new IdNotFoundException("surveys"));
  }

  @Override
  public SurveyResponse create(SurveyRequest request) {
    User user = this.userService.findEntity(request.getUserId());

    Survey survey = this.surveyMapper.requestToSurvey(request);
    survey.setUser(user);

    return this.surveyMapper.surveyToResponse(this.surveyRepository.save(survey));
  }

  @Override
  public SurveyResponse update(Long id, SurveyRequest request) {
    Survey survey = findEntity(id);
    if (survey.getUser().getId().equals(request.getUserId())) {
      User user = this.userService.findEntity(request.getUserId());
      survey.setUser(user);
    }
    survey = this.surveyMapper.requestToSurvey(request);
    return this.surveyMapper.surveyToResponse(this.surveyRepository.save(survey));
  }

  @Override
  public void delete(Long id) {
    Survey survey = findEntity(id);
    this.surveyRepository.delete(survey);
  }

}
