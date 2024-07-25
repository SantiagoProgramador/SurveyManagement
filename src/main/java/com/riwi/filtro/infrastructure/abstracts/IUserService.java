package com.riwi.filtro.infrastructure.abstracts;

import com.riwi.filtro.api.dto.request.UserRequest;
import com.riwi.filtro.api.dto.request.update.UserUpdateRequest;
import com.riwi.filtro.api.dto.response.UserResponse;
import com.riwi.filtro.domain.entities.User;

public interface IUserService extends BaseService<User, UserRequest, UserResponse, Long, UserUpdateRequest> {

}
