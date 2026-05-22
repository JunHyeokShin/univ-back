package com.hyk.univ.enrollment.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "enrollments",
    uniqueConstraints = @UniqueConstraint(columnNames = {"student_id", "course_id"}))
@Entity
public class Enrollment {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "student_id", nullable = false)
  private Long studentId;

  @Column(name = "course_id", nullable = false)
  private Long courseId;

  @Column(nullable = false)
  private String semester;

  @Column(nullable = false)
  private int credit;

  private Enrollment(Long studentId, Long courseId, String semester, int credit) {
    this.studentId = studentId;
    this.courseId = courseId;
    this.semester = semester;
    this.credit = credit;
  }

  public static Enrollment create(Long studentId, Long courseId, String semester, int credit) {
    return new Enrollment(studentId, courseId, semester, credit);
  }

}
