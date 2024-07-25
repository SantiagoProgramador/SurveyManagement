package com.riwi.filtro.domain.entities;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity(name = "users")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(length = 100, nullable = false)
  private String name;

  @Column(length = 100, nullable = false)
  private String email;

  @Column(length = 255, nullable = false)
  private String password;

  @Column(nullable = false)
  @Builder.Default
  private boolean active = true;

  @OneToMany(mappedBy = "user", fetch = FetchType.EAGER, cascade = CascadeType.PERSIST, orphanRemoval = false)
  @ToString.Exclude
  @EqualsAndHashCode.Exclude
  private List<Survey> surveys;
}
