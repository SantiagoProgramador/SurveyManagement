package com.riwi.filtro.domain.entities;

import java.io.Serializable;
import java.util.List;

import com.riwi.filtro.utils.enums.QuestionType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Question implements Serializable {

  private Long id;

  private String text;

  private boolean active = true;

  private QuestionType type;

  private Survey survey;

  private List<OptionQuestion> options;
}
