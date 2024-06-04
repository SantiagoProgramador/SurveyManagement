package com.riwi.filtro.api.dto.request;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SurveyRequest {

  @NotBlank(message = "The title of the survey is required")
  @Length(min = 5, max = 255, message = "The length of the title is invalid")
  private String title;

  @NotBlank(message = "Description is empty")
  private String description;

  @NotNull(message = "The user is required")
  private Long userId;

}
