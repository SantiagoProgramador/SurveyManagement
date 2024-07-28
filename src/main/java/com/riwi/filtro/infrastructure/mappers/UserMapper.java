package com.riwi.filtro.infrastructure.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.riwi.filtro.api.dto.request.UserRequest;
import com.riwi.filtro.api.dto.request.update.UserUpdateRequest;
import com.riwi.filtro.api.dto.response.UserResponse;
import com.riwi.filtro.api.dto.response.UserToSurvey;
import com.riwi.filtro.domain.entities.User;

@Mapper(componentModel = "spring")
public interface UserMapper {

  UserResponse userToResponse(User user);

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "surveys", ignore = true)
  @Mapping(target = "enabled", ignore = true)
  @Mapping(target = "roles", ignore = true)
  @Mapping(target = "authorities", ignore = true)
  @Mapping(target = "active", ignore = true)
  User requestToUser(UserRequest userRequest);

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "surveys", ignore = true)
  @Mapping(target = "enabled", ignore = true)
  @Mapping(target = "roles", ignore = true)
  @Mapping(target = "authorities", ignore = true)
  User updateToUser(UserUpdateRequest updateRequest);

  UserToSurvey userToUserToSurvey(User user);
}
