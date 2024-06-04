package com.riwi.filtro.api.dto.request;

import java.util.List;

import com.riwi.filtro.utils.enums.QuestionType;

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
  private QuestionType type;

  @NotNull(message = "The id of the survey is required")
  private Long surveyId;

  private List<OptionQuestionRequest> options;

  private boolean active;
}
