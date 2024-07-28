package com.riwi.filtro.api.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserToSurvey {
  private Long id;
  private String name;
  private String userName;
  private String email;
  private boolean active;
}
