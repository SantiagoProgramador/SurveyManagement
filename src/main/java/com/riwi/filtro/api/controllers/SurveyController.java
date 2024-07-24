package com.riwi.filtro.api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.riwi.filtro.api.dto.errors.ErrorResponse;
import com.riwi.filtro.api.dto.request.SurveyRequest;
import com.riwi.filtro.api.dto.response.SurveyResponse;
import com.riwi.filtro.infrastructure.abstracts.ISurveyService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping(path = "/surveys")
@AllArgsConstructor
public class SurveyController {

  @Autowired
  private final ISurveyService iSurveyService;

  @Operation(summary = "Show the surveys by pagination")
  @ApiResponse(responseCode = "400", content = {
      @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)) })
  @GetMapping
  public ResponseEntity<Page<SurveyResponse>> showAll(@RequestParam(defaultValue = "1") int page,
      @RequestParam(defaultValue = "3") int size) {

    return ResponseEntity.ok(this.iSurveyService.getAll(size, page - 1));
  }

  @Operation(summary = "Show the survey according to the id given")
  @ApiResponse(responseCode = "400", content = {
      @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)) })
  @GetMapping(path = "/{id}")
  public ResponseEntity<SurveyResponse> showSurvey(@PathVariable Long id) {

    return ResponseEntity.ok(this.iSurveyService.getById(id));
  }

  @Operation(summary = "Create a survey with the information given")
  @ApiResponse(responseCode = "400", content = {
      @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)) })
  @PostMapping(path = "/add")
  public ResponseEntity<SurveyResponse> addSurvey(@Validated @RequestBody SurveyRequest surveyRequest) {

    return ResponseEntity.ok(this.iSurveyService.create(surveyRequest));
  }

  @Operation(summary = "Update a survey according to the id given and the information summoned")
  @ApiResponse(responseCode = "400", content = {
      @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)) })
  @PutMapping(path = "/update/{id}")
  public ResponseEntity<SurveyResponse> updateSurvey(@Validated @RequestBody SurveyRequest surveyRequest,
      @PathVariable Long id) {

    return ResponseEntity.ok(this.iSurveyService.update(id, surveyRequest));
  }

  @Operation(summary = "Delete a survey according to the id given")
  @ApiResponse(responseCode = "400", content = {
      @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)) })
  @DeleteMapping(path = "/{id}")
  public ResponseEntity<Void> deleteSurvey(@PathVariable Long id) {
    this.iSurveyService.delete(id);

    return ResponseEntity.noContent().build();
  }
}
