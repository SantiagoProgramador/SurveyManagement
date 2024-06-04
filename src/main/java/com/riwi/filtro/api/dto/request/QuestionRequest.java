package com.riwi.filtro.api.dto.request;

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
public class QuestionRequest {

  @NotBlank(message = "Type the text of the question")
  private String text;

  @NotNull(message = "The type of the question is required")
  private String type;

  @NotNull(message = "The id of the survey is required")
  private Long surveyId;

}
