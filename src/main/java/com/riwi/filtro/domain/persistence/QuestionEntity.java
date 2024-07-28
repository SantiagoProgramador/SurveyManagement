package com.riwi.filtro.domain.persistence;

import java.io.Serializable;
import java.util.List;

import com.riwi.filtro.utils.enums.QuestionType;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity(name = "questions")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuestionEntity implements Serializable {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Lob
  @Column(nullable = false)
  private String text;

  @Column(nullable = false)
  private boolean active = true;

  @Column(nullable = false)
  @Enumerated(EnumType.STRING)
  private QuestionType type;

  @ToString.Exclude
  @EqualsAndHashCode.Exclude
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "survey_id", referencedColumnName = "id")
  private SurveyEntity survey;

  @OneToMany(mappedBy = "question", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = false)
  @ToString.Exclude
  @EqualsAndHashCode.Exclude
  private List<OptionQuestionEntity> options;
}