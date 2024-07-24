package com.riwi.filtro.utils.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class QuestionTypeException extends RuntimeException {
  private static final String ERROR_MESSAGE_1 = "Incorrect question type for the parameters send";

  public QuestionTypeException() {
    super(ERROR_MESSAGE_1);

  }
}
