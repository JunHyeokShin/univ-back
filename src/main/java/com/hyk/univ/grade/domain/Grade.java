package com.hyk.univ.grade.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
@Table(name = "grades",
    uniqueConstraints = @UniqueConstraint(columnNames = {"student_id", "course_id"}))
@Entity
public class Grade {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private Long studentId;

  @Column(nullable = false)
  private Long courseId;

  @Column(nullable = false)
  private String semester;

  @Embedded
  private Score score;

  @Embedded
  private Attendance attendance;

  @Column(nullable = false)
  @Enumerated(EnumType.STRING)
  private LetterGrade letterGrade;

  private Grade(Long studentId, Long courseId, String semester) {
    this.studentId = studentId;
    this.courseId = courseId;
    this.semester = semester;
    this.score = new Score(0, 0, 0, 0);
    this.attendance = Attendance.empty();
    this.letterGrade = LetterGrade.NOT_ASSIGNED;
  }

  public static Grade initial(Long studentId, Long courseId, String semester) {
    return new Grade(studentId, courseId, semester);
  }

  public void updateScore(Score newScore, GradeCalculator calculator) {
    this.score = newScore;
    this.letterGrade = calculator.calculate(this.score, this.attendance);
  }

  public void updateAttendance(Attendance newAttendance, GradeCalculator calculator) {
    this.attendance = newAttendance;
    this.letterGrade = calculator.calculate(this.score, this.attendance);
  }

}
