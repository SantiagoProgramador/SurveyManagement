package com.riwi.filtro.utils.exceptions;

public class IdNotFoundException extends RuntimeException {
  private static final String ERROR_MESSAGE = "The id supplied for the entity %s was not found :(";

  public IdNotFoundException(String nameEntity) {
    super(String.format(ERROR_MESSAGE, nameEntity));
  }
}
