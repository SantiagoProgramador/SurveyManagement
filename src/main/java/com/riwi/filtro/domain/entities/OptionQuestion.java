package com.riwi.filtro.domain.entities;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OptionQuestion implements Serializable {

  private Long id;

  private String text;

  private boolean active = true;

  private Question question;
}
