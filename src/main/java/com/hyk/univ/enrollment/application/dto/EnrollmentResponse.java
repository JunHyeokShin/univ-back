package com.hyk.univ.enrollment.application.dto;

import java.time.LocalDateTime;

import com.hyk.univ.enrollment.domain.Enrollment;

public record EnrollmentResponse(
    Long id,
    Long studentId,
    Long courseId,
    String semester,
    LocalDateTime enrolledAt
) {

  public static EnrollmentResponse from(Enrollment enrollment) {
    return new EnrollmentResponse(
        enrollment.getId(),
        enrollment.getStudentId(),
        enrollment.getCourseId(),
        enrollment.getSemester(),
        enrollment.getEnrolledAt()
    );
  }

}
