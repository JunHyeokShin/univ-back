package com.hyk.univ.enrollment.application;

import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hyk.univ.common.exception.BusinessException;
import com.hyk.univ.common.exception.ErrorCode;
import com.hyk.univ.course.application.CourseService;
import com.hyk.univ.course.application.dto.CourseResponse;
import com.hyk.univ.enrollment.application.dto.EnrollmentResponse;
import com.hyk.univ.enrollment.domain.Enrollment;
import com.hyk.univ.enrollment.domain.EnrollmentRepository;
import com.hyk.univ.schedule.application.RequireMode;
import com.hyk.univ.schedule.domain.AcademicMode;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class EnrollmentService {

  private final EnrollmentRepository enrollmentRepository;
  private final EnrollmentPolicy enrollmentPolicy;
  private final CourseService courseService;

  @RequireMode(AcademicMode.ENROLLMENT)
  @Transactional
  public EnrollmentResponse enroll(Long studentId, Long courseId) {
    CourseResponse course = this.courseService.findById(courseId);
    this.enrollmentPolicy.validate(studentId, courseId, course.semester(), course.credit());
    this.courseService.increaseEnrollment(courseId);
    Enrollment enrollment = Enrollment.create(studentId, courseId, course.semester(), course.credit());
    return EnrollmentResponse.from(this.enrollmentRepository.save(enrollment));
  }

  @RequireMode(AcademicMode.ENROLLMENT)
  @Transactional
  public void cancel(Long enrollmentId, Long studentId) {
    Enrollment enrollment = this.enrollmentRepository.findById(enrollmentId)
        .orElseThrow(() -> new BusinessException(ErrorCode.ENROLLMENT_NOT_FOUND));
    if (!enrollment.getStudentId().equals(studentId)) {
      throw new BusinessException(ErrorCode.ENROLLMENT_NOT_FOUND);
    }
    this.courseService.decreaseEnrollment(enrollment.getCourseId());
    this.enrollmentRepository.deleteById(enrollmentId);
  }

  public List<EnrollmentResponse> findMyEnrollments(Long studentId) {
    return this.enrollmentRepository.findByStudentId(studentId).stream()
        .map(EnrollmentResponse::from).toList();
  }

  public List<EnrollmentResponse> findByCourse(Long courseId) {
    return this.enrollmentRepository.findByCourseId(courseId).stream()
        .map(EnrollmentResponse::from).toList();
  }

}
