package com.hyk.univ.enrollment.application;

import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hyk.univ.common.exception.BusinessException;
import com.hyk.univ.common.exception.ErrorCode;
import com.hyk.univ.course.domain.Course;
import com.hyk.univ.course.domain.CourseRepository;
import com.hyk.univ.enrollment.application.dto.EnrollmentResponse;
import com.hyk.univ.enrollment.domain.Enrollment;
import com.hyk.univ.enrollment.domain.EnrollmentPolicy;
import com.hyk.univ.enrollment.domain.EnrollmentRepository;
import com.hyk.univ.schedule.application.RequireMode;
import com.hyk.univ.schedule.domain.AcademicMode;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class EnrollmentService {

  private final EnrollmentRepository enrollmentRepository;
  private final CourseRepository courseRepository;
  private final EnrollmentPolicy enrollmentPolicy;

  public List<EnrollmentResponse> myEnrollments(Long studentId) {
    return this.enrollmentRepository.findByStudentId(studentId).stream()
        .map(EnrollmentResponse::from).toList();
  }

  public List<EnrollmentResponse> studentsOfCourse(Long courseId) {
    return this.enrollmentRepository.findByCourseId(courseId).stream()
        .map(EnrollmentResponse::from).toList();
  }

  @Transactional
  @RequireMode(AcademicMode.ENROLLMENT)
  public EnrollmentResponse enroll(Long studentId, Long courseId) {
    Course course = this.courseRepository.findById(courseId)
        .orElseThrow(() -> new BusinessException(ErrorCode.COURSE_NOT_FOUND));

    boolean already = this.enrollmentRepository.existsByStudentIdAndCourseId(studentId, courseId);
    this.enrollmentPolicy.checkDuplicate(already);

    List<Enrollment> existings = this.enrollmentRepository.findByStudentIdAndSemester(studentId, course.getSemester());
    int currentTotalCrdeit = existings.stream()
        .map(existing ->
            this.courseRepository.findById(existing.getCourseId()).map(Course::getCredit).orElse(0))
        .mapToInt(Integer::intValue).sum();
    this.enrollmentPolicy.checkCreditLimit(currentTotalCrdeit, course.getCredit());

    course.increaseEnrollment();

    Enrollment enrollment = Enrollment.of(studentId, courseId, course.getSemester());
    return EnrollmentResponse.from(this.enrollmentRepository.save(enrollment));
  }

  @Transactional
  @RequireMode(AcademicMode.ENROLLMENT)
  public void cancel(Long studentId, Long enrollmentId) {
    Enrollment enrollment = this.enrollmentRepository.findById(enrollmentId)
        .orElseThrow(() -> new BusinessException(ErrorCode.ENROLLMENT_NOT_FOUND));
    if (!enrollment.getStudentId().equals(studentId)) {
      throw new BusinessException(ErrorCode.FORBIDDEN);
    }
    this.courseRepository.findById(enrollment.getCourseId()).ifPresent(Course::decreaseEnrollment);
    this.enrollmentRepository.deleteById(enrollmentId);
  }

}
