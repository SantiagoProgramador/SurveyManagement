package com.riwi.filtro.api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.riwi.filtro.api.dto.request.QuestionRequest;
import com.riwi.filtro.api.dto.response.QuestionResponse;
import com.riwi.filtro.infrastructure.abstracts.IQuestionService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping(path = "/questions")
@AllArgsConstructor
public class QuestionController {

  @Autowired
  private final IQuestionService iQuestionService;

  @GetMapping
  public ResponseEntity<Page<QuestionResponse>> showAll(@RequestParam(defaultValue = "1") int page,
      @RequestParam(defaultValue = "3") int size) {

    return ResponseEntity.ok(this.iQuestionService.getAll(size, page - 1));
  }

  @GetMapping(path = "/{id}")
  public ResponseEntity<QuestionResponse> showQuestion(@PathVariable Long id) {

    return ResponseEntity.ok(this.iQuestionService.getById(id));
  }

  @PostMapping(path = "/add")
  public ResponseEntity<QuestionResponse> addQuestion(@RequestBody QuestionRequest questionRequest) {

    return ResponseEntity.ok(this.iQuestionService.create(questionRequest));
  }

  @PutMapping(path = "/update/{id}")
  public ResponseEntity<QuestionResponse> updateQuestion(@RequestBody QuestionRequest questionRequest,
      @PathVariable Long id) {

    return ResponseEntity.ok(this.iQuestionService.update(id, questionRequest));
  }

  @DeleteMapping(path = "/{id}")
  public ResponseEntity<Void> deleteQuestion(@PathVariable Long id) {
    this.iQuestionService.delete(id);
    return ResponseEntity.noContent().build();
  }
}
