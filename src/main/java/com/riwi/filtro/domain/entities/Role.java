package com.riwi.filtro.domain.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Role implements Serializable {

  private Long id;

  private String name;

  private Set<User> users;
}
