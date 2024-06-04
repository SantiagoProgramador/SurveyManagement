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

import com.riwi.filtro.api.dto.request.SurveyRequest;
import com.riwi.filtro.api.dto.response.SurveyResponse;
import com.riwi.filtro.infrastructure.abstracts.ISurveyService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping(path = "/surveys")
@AllArgsConstructor
public class SurveyController {

  @Autowired
  private final ISurveyService iSurveyService;

  @GetMapping
  public ResponseEntity<Page<SurveyResponse>> showAll(@RequestParam(defaultValue = "1") int page,
      @RequestParam(defaultValue = "3") int size) {

    return ResponseEntity.ok(this.iSurveyService.getAll(size, page - 1));
  }

  @GetMapping(path = "/{id}")
  public ResponseEntity<SurveyResponse> showSurvey(@PathVariable Long id) {

    return ResponseEntity.ok(this.iSurveyService.getById(id));
  }

  @PostMapping(path = "/add")
  public ResponseEntity<SurveyResponse> addSurvey(@Validated @RequestBody SurveyRequest surveyRequest) {

    return ResponseEntity.ok(this.iSurveyService.create(surveyRequest));
  }

  @PutMapping(path = "/update/{id}")
  public ResponseEntity<SurveyResponse> updateSurvey(@Validated @RequestBody SurveyRequest surveyRequest,
      @PathVariable Long id) {

    return ResponseEntity.ok(this.iSurveyService.update(id, surveyRequest));
  }

  @DeleteMapping(path = "/{id}")
  public ResponseEntity<Void> deleteSurvey(@PathVariable Long id) {
    this.iSurveyService.delete(id);

    return ResponseEntity.noContent().build();
  }
}
