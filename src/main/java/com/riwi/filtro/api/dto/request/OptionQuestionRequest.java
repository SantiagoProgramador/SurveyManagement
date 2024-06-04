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
public class OptionQuestionRequest {

  private Long id;

  @NotBlank(message = "Type the text of the option")
  private String text;

  @NotNull(message = "The status of the option is required")
  private boolean active;
}
