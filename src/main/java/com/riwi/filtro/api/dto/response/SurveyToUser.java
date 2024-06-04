package com.riwi.filtro.api.dto.response;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SurveyToUser {
  private Long id;
  private String title;
  private String description;
  private LocalDateTime creationDate;
  private boolean active;

}
