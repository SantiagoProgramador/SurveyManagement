package com.riwi.filtro.infrastructure.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.riwi.filtro.api.dto.request.UserRequest;
import com.riwi.filtro.api.dto.request.update.UserUpdateRequest;
import com.riwi.filtro.api.dto.response.UserResponse;
import com.riwi.filtro.domain.entities.Role;
import com.riwi.filtro.domain.entities.User;
import com.riwi.filtro.domain.repositories.UserRepository;
import com.riwi.filtro.infrastructure.abstracts.IUserService;
import com.riwi.filtro.infrastructure.mappers.SurveyMapper;
import com.riwi.filtro.infrastructure.mappers.UserMapper;
import com.riwi.filtro.utils.exceptions.IdNotFoundException;
import com.riwi.filtro.utils.exceptions.UserNameException;

import java.util.HashSet;
import java.util.Set;

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

    return this.userRepository.findAll(pageable).map(userMapper::userToResponse);
  }

  @Override
  public UserResponse getById(Long id) {
    User user = findEntity(id);
    UserResponse userResponse = this.userMapper.userToResponse(user);

    if (user.getSurveys() != null) {
      userResponse.setSurveys(user.getSurveys().stream().map(surveyMapper::surveyToSurveyToUser).toList());
    }
    return userResponse;
  }

  @Override
  public User findEntity(Long id) {
    return this.userRepository.findById(id).orElseThrow(() -> new IdNotFoundException("users"));
  }

  @Override
  public UserResponse create(UserRequest request) {
    this.userNameException(request.getUserName());

    User user = this.userMapper.requestToUser(request);

    return this.userMapper.userToResponse(this.userRepository.save(user));
  }

  @Override
  public UserResponse update(Long id, UserUpdateRequest request) {
    this.userNameException(request.getUserName());

    User existingUser = findEntity(id);

    User updatedUser = this.userMapper.updateToUser(request);

    updatedUser.setId(existingUser.getId());

    return this.userMapper.userToResponse(this.userRepository.save(updatedUser));
  }

  @Override
  public void delete(Long id) {
    User user = findEntity(id);
    this.userRepository.delete(user);
  }

  private UserNameException userNameException(String userName) {
    if (this.userRepository.findByUserName(userName) != null) {
      return new UserNameException(userName);

    }
    return null;
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    User user = this.userRepository.findByUserName(username);
    if (user == null) {
      throw new UsernameNotFoundException("Username not found: " + username);
    }
    Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
    for (Role role : user.getRoles()) {
      grantedAuthorities.add(new SimpleGrantedAuthority(role.getName()));
    }
    return user;
  }
}
