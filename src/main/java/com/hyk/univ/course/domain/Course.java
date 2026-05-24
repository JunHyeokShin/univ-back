package com.hyk.univ.course.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import com.hyk.univ.common.exception.BusinessException;
import com.hyk.univ.common.exception.ErrorCode;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "courses")
@Entity
public class Course {

  private static final int MIN_ENROLLMENT_TO_KEEP_OPEN = 3;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private String name;

  @Column(nullable = false)
  @Enumerated(EnumType.STRING)
  private CourseType type;

  @Column(nullable = false)
  private int credit;

  @Column(nullable = false)
  private int capacity;

  @Column(nullable = false)
  private int currentEnrollment;

  @Column(nullable = false)
  private Long professorId;

  @Column(nullable = false)
  private String semester;

  @Column(nullable = false)
  @Enumerated(EnumType.STRING)
  private CourseStatus status;

  private Course(String name, CourseType type, int credit,
      int capacity, Long professorId, String semester) {
    this.name = name;
    this.type = type;
    this.credit = credit;
    this.capacity = capacity;
    this.currentEnrollment = 0;
    this.professorId = professorId;
    this.semester = semester;
    this.status = CourseStatus.OPEN;
  }

  public static Course open(String name, CourseType type, int credit,
      int capacity, Long professorId, String semester) {
    return new Course(name, type, credit, capacity, professorId, semester);
  }

  public boolean isFull() {
    return this.currentEnrollment >= this.capacity;
  }

  public void increaseEnrollment() {
    if (this.status == CourseStatus.CLOSED) {
      throw new BusinessException(ErrorCode.COURSE_CLOSED);
    }
    if (isFull()) {
      throw new BusinessException(ErrorCode.COURSE_FULL);
    }
    this.currentEnrollment++;
  }

  public void decreaseEnrollment() {
    if (this.currentEnrollment > 0) {
      this.currentEnrollment--;
    }
  }

  public void close() {
    if (this.currentEnrollment >= MIN_ENROLLMENT_TO_KEEP_OPEN) {
      throw new BusinessException(ErrorCode.CANNOT_CLOSE_COURSE);
    }
    this.status = CourseStatus.CLOSED;
  }

}
