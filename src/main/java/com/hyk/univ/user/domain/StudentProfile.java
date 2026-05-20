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
public class StudentProfile {

  @Column(name = "student_number", unique = true)
  private String studentNumber;

  @Column(name = "student_department")
  private String department;

}
