package com.riwi.filtro.api.dto.response;

import java.util.List;

import com.riwi.filtro.utils.enums.QuestionType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QuestionResponse {
  private Long id;
  private String text;
  private QuestionType type;
  private Boolean active;
  private List<OptionQuestionResponse> options;
}
