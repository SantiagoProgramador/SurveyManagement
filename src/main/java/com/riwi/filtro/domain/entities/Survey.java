package com.riwi.filtro.domain.entities;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Survey implements Serializable {

  private Long id;

  private String title;

  private String description;

  private LocalDateTime creationDate = LocalDateTime.now();

  private boolean active = true;

  private User user;

  private List<Question> questions;
}
