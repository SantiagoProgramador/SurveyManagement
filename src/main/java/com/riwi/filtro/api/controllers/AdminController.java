package com.riwi.filtro.api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.riwi.filtro.api.dto.errors.ErrorResponse;
import com.riwi.filtro.api.dto.response.UserResponse;
import com.riwi.filtro.infrastructure.abstracts.IUserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping(path = "/admin")
public class AdminController {
  @Autowired
  private final IUserService userService;

  @Operation(summary = "Show the users in pages")
  @ApiResponse(responseCode = "400", content = {
      @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)) })
  @GetMapping
  public ResponseEntity<Page<UserResponse>> showAll(@RequestParam(defaultValue = "1") int page,
      @RequestParam(defaultValue = "3") int size) {

    return ResponseEntity.ok(this.userService.getAll(size, page - 1));
  }
}
