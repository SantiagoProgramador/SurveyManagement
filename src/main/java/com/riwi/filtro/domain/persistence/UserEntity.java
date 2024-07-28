package com.riwi.filtro.domain.persistence;

import java.util.List;
import java.io.Serializable;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Set;

import com.riwi.filtro.domain.entities.Survey;

@Entity(name = "users")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserEntity implements Serializable {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(length = 100, nullable = false)
  private String name;

  @Column(length = 100, nullable = false, unique = true)
  private String username;

  @Column(length = 100, nullable = false)
  private String email;

  @Column(length = 255, nullable = false)
  private String password;

  @Column(nullable = false)
  private boolean active = true;

  @OneToMany(mappedBy = "user", fetch = FetchType.EAGER, cascade = CascadeType.PERSIST, orphanRemoval = false)
  @ToString.Exclude
  @EqualsAndHashCode.Exclude
  private List<Survey> surveys;

  @ManyToMany
  @JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
  private Set<RoleEntity> roles;

  private boolean enabled = true;
}
