package com.riwi.filtro.api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.riwi.filtro.api.dto.errors.ErrorResponse;
import com.riwi.filtro.api.dto.request.QuestionRequest;
import com.riwi.filtro.api.dto.response.QuestionResponse;
import com.riwi.filtro.infrastructure.abstracts.IQuestionService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping(path = "/questions")
@AllArgsConstructor
public class QuestionController {

  @Autowired
  private final IQuestionService iQuestionService;

  @Operation(summary = "Show the questions by pagination")
  @ApiResponse(responseCode = "400", description = "When the params send are invalid", content = {
      @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)) })
  @GetMapping
  public ResponseEntity<Page<QuestionResponse>> showAll(@RequestParam(defaultValue = "1") int page,
      @RequestParam(defaultValue = "3") int size) {

    return ResponseEntity.ok(this.iQuestionService.getAll(size, page - 1));
  }

  @Operation(summary = "Show the question according to the id given")
  @ApiResponse(responseCode = "400", description = "When the id given is not found", content = {
      @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)) })
  @GetMapping(path = "/{id}")
  public ResponseEntity<QuestionResponse> showQuestion(@PathVariable Long id) {

    return ResponseEntity.ok(this.iQuestionService.getById(id));
  }

  @Operation(summary = "Create a question with the information given")
  @ApiResponse(responseCode = "400", description = "When the information given is incorrect", content = {
      @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)) })
  @PostMapping(path = "/add")
  public ResponseEntity<QuestionResponse> addQuestion(@RequestBody QuestionRequest questionRequest) {

    return ResponseEntity.ok(this.iQuestionService.create(questionRequest));
  }

  @Operation(summary = "Update a question according to the id summoned")
  @ApiResponse(responseCode = "400", description = "When the id given is not found or the information is incorrect", content = {
      @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)) })
  @PutMapping(path = "/update/{id}")
  public ResponseEntity<QuestionResponse> updateQuestion(@RequestBody QuestionRequest questionRequest,
      @PathVariable Long id) {

    return ResponseEntity.ok(this.iQuestionService.update(id, questionRequest));
  }

  @Operation(summary = "Delete a question according to the id given")
  @ApiResponse(responseCode = "400", description = "When the id given is not found", content = {
      @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)) })
  @DeleteMapping(path = "/{id}")
  public ResponseEntity<Void> deleteQuestion(@PathVariable Long id) {
    this.iQuestionService.delete(id);
    return ResponseEntity.noContent().build();
  }

  @Operation(summary = "Update a question according to the id summoned without the options")
  @ApiResponse(responseCode = "400", description = "When the id given is not found or the information is incorrect", content = {
      @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)) })
  @PatchMapping(path = "/update/{id}")
  public ResponseEntity<QuestionResponse> updateQuestionWithoutOptions(@RequestBody QuestionRequest questionRequest,
      @PathVariable Long id) {

    return ResponseEntity.ok(this.iQuestionService.updateWithoutOptions(id, questionRequest));
  }
}
