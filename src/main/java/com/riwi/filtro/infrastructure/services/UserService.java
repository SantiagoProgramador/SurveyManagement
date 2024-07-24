package com.riwi.filtro.infrastructure.services;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.riwi.filtro.api.dto.request.UserRequest;
import com.riwi.filtro.api.dto.response.SurveyToUser;
import com.riwi.filtro.api.dto.response.UserResponse;
import com.riwi.filtro.domain.entities.Survey;
import com.riwi.filtro.domain.entities.User;
import com.riwi.filtro.domain.repositories.UserRepository;
import com.riwi.filtro.infrastructure.abstracts.IUserService;
import com.riwi.filtro.infrastructure.mappers.SurveyMapper;
import com.riwi.filtro.infrastructure.mappers.UserMapper;
import com.riwi.filtro.utils.exceptions.IdNotFoundException;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserService implements IUserService {

  @Autowired
  private final UserRepository userRepository;

  @Autowired
  private final UserMapper userMapper;

  @Autowired
  private final SurveyMapper surveyMapper;

  @Override
  public Page<UserResponse> getAll(int size, int page) {
    if (page < 0) {
      page = 0;
    }
    Pageable pageable = PageRequest.of(page, size);

    return this.userRepository.findAll(pageable).map(this::userToUserResponse);
  }

  @Override
  public UserResponse getById(Long id) {
    User user = findEntity(id);
    UserResponse userResponse = new UserResponse();

    BeanUtils.copyProperties(user, userResponse);
    if (user.getSurveys() != null) {
      userResponse.setSurveys(user.getSurveys().stream().map(this::surveyToSurveyToUser).toList());
    }
    return userResponse;
  }

  @Override
  public User findEntity(Long id) {
    return this.userRepository.findById(id).orElseThrow(() -> new IdNotFoundException("users"));
  }

  @Override
  public UserResponse create(UserRequest request) {
    User user = new User();

    this.userRequestToUser(request, user);

    return this.userToUserResponse(this.userRepository.save(user));
  }

  @Override
  public UserResponse update(Long id, UserRequest request) {
    User user = findEntity(id);

    this.userRequestToUser(request, user);

    return this.userToUserResponse(this.userRepository.save(user));
  }

  @Override
  public void delete(Long id) {
    User user = findEntity(id);
    this.userRepository.delete(user);
  }

  private UserResponse userToUserResponse(User user) {
    UserResponse userResponse = new UserResponse();

    BeanUtils.copyProperties(user, userResponse);
    return userResponse;
  }

  private User userRequestToUser(UserRequest userRequest, User user) {
    BeanUtils.copyProperties(userRequest, user);

    return user;
  }

  private SurveyToUser surveyToSurveyToUser(Survey survey) {
    SurveyToUser surveyToUser = new SurveyToUser();
    BeanUtils.copyProperties(survey, surveyToUser);
    return surveyToUser;
  }

}
