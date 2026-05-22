package com.hyk.univ.enrollment.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import com.hyk.univ.common.exception.BusinessException;
import com.hyk.univ.common.exception.ErrorCode;
import com.hyk.univ.enrollment.domain.EnrollmentRepository;

@RequiredArgsConstructor
@Service
public class EnrollmentPolicy {

  private static final int MAX_CREDITS_PER_SEMESTER = 18;

  private final EnrollmentRepository enrollmentRepository;

  public void validate(Long studentId, Long courseId, String semester, int credit) {
    if (this.enrollmentRepository.existsByStudentIdAndCourseId(studentId, courseId)) {
      throw new BusinessException(ErrorCode.ENROLLMENT_ALREADY_EXISTS);
    }
    int currentCredits = this.enrollmentRepository.findByStudentIdAndSemester(studentId, semester)
        .stream().mapToInt(e -> e.getCredit()).sum();
    if (currentCredits + credit > MAX_CREDITS_PER_SEMESTER) {
      throw new BusinessException(ErrorCode.MAX_CREDIT_EXCEEDED);
    }
  }

}
