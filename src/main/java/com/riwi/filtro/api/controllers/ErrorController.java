package com.riwi.filtro.api.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.riwi.filtro.api.dto.errors.BaseErrorResponse;
import com.riwi.filtro.api.dto.errors.ErrorResponse;
import com.riwi.filtro.api.dto.errors.ErrorsResponse;
import com.riwi.filtro.utils.exceptions.IdNotFoundException;

@RestControllerAdvice
@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class ErrorController {
  @ExceptionHandler(IdNotFoundException.class)
  public BaseErrorResponse idNotFound(IdNotFoundException exception) {

    return ErrorResponse.builder().message(exception.getMessage()).status(HttpStatus.BAD_REQUEST.name())
        .code(HttpStatus.BAD_REQUEST.value()).build();
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public BaseErrorResponse errorsResponse(MethodArgumentNotValidException exception) {
    List<String> errors = new ArrayList<>();

    exception.getAllErrors().forEach(error -> errors.add(error.getDefaultMessage()));

    return ErrorsResponse.builder().errors(errors).status(HttpStatus.BAD_REQUEST.name())
        .code(HttpStatus.BAD_REQUEST.value()).build();
  }
}
