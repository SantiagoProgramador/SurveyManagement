package com.riwi.filtro.utils.exceptions;

public class UserNameException extends RuntimeException {
  private static final String ERROR_MSG = "The userName %s is already used";

  public UserNameException(String userName) {
    super(String.format(ERROR_MSG, userName));
  }
}
