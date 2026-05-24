package com.hyk.univ.grade.application.dto;

import com.hyk.univ.grade.domain.Grade;
import com.hyk.univ.grade.domain.LetterGrade;

public record GradeResponse(
    Long id,
    Long studentId,
    Long courseId,
    String semester,
    int attendanceScore,
    int assignmentScore,
    int midtermScore,
    int finalScore,
    int total,
    int presentCount,
    int lateCount,
    int absentCount,
    LetterGrade letterGrade
) {

  public static GradeResponse from(Grade grade) {
    return new GradeResponse(
        grade.getId(),
        grade.getStudentId(),
        grade.getCourseId(),
        grade.getSemester(),
        grade.getScore().getAttendanceScore(),
        grade.getScore().getAssignmentScore(),
        grade.getScore().getMidtermScore(),
        grade.getScore().getFinalScore(),
        grade.getScore().total(),
        grade.getAttendance().getPresentCount(),
        grade.getAttendance().getLateCount(),
        grade.getAttendance().getAbsentCount(),
        grade.getLetterGrade()
    );
  }

}
