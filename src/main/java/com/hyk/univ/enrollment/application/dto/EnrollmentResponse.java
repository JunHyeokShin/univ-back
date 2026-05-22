package com.hyk.univ.enrollment.application.dto;

import com.hyk.univ.enrollment.domain.Enrollment;

public record EnrollmentResponse(
    Long id,
    Long studentId,
    Long courseId,
    String semester,
    int credit
) {

  public static EnrollmentResponse from(Enrollment enrollment) {
    return new EnrollmentResponse(
        enrollment.getId(),
        enrollment.getStudentId(),
        enrollment.getCourseId(),
        enrollment.getSemester(),
        enrollment.getCredit()
    );
  }

}
