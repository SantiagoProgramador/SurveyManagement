package com.riwi.filtro.api.dto.request.update;

import com.riwi.filtro.api.dto.request.UserRequest;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class UserUpdateRequest extends UserRequest {
  private boolean active;
}
