package com.hyk.univ.enrollment.domain;

import java.time.LocalDateTime;

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

  @Column(nullable = false)
  private Long studentId;

  @Column(nullable = false)
  private Long courseId;

  @Column(nullable = false)
  private String semester;

  @Column(nullable = false)
  private LocalDateTime enrolledAt;

  private Enrollment(Long studentId, Long courseId, String semester) {
    this.studentId = studentId;
    this.courseId = courseId;
    this.semester = semester;
    this.enrolledAt = LocalDateTime.now();
  }

  public static Enrollment of(Long studentId, Long courseId, String semester) {
    return new Enrollment(studentId, courseId, semester);
  }

}
