package com.riwi.filtro.infrastructure.services;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.riwi.filtro.api.dto.request.UserRequest;
import com.riwi.filtro.api.dto.response.UserResponse;
import com.riwi.filtro.domain.entities.User;
import com.riwi.filtro.domain.repositories.UserRepository;
import com.riwi.filtro.infrastructure.abstracts.IUserService;
import com.riwi.filtro.utils.exceptions.IdNotFoundException;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserService implements IUserService {

  @Autowired
  private final UserRepository userRepository;

  @Override
  public List<UserResponse> getAll() {

    return this.userRepository.findAll().stream().map(this::userToUserResponse).toList();
  }

  @Override
  public UserResponse getById(Long id) {
    User user = findUser(id);

    return this.userToUserResponse(user);
  }

  private User findUser(Long id) {
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
    User user = findUser(id);

    this.userRequestToUser(request, user);

    return this.userToUserResponse(this.userRepository.save(user));
  }

  @Override
  public void delete(Long id) {
    User user = findUser(id);
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
}
