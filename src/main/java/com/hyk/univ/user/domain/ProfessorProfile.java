package com.hyk.univ.user.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Embeddable
public class ProfessorProfile {

  @Column(name = "professor_number", unique = true)
  private String professorNumber;

  @Column(name = "professor_department")
  private String department;

}
