package com.riwi.filtro.api.dto.response;

import java.util.List;
import java.util.Set;

import com.riwi.filtro.domain.entities.Role;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {
  private Long id;
  private String name;
  private String username;
  private String email;
  private boolean active;
  private List<SurveyToUser> surveys;
  private Set<Role> roles;
}
