package com.hyk.univ.enrollment.infrastructure;

import java.util.List;
import java.util.Optional;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import com.hyk.univ.enrollment.domain.Enrollment;
import com.hyk.univ.enrollment.domain.EnrollmentRepository;

@RequiredArgsConstructor
@Repository
public class EnrollmentRepositoryImpl implements EnrollmentRepository {

  private final JpaEnrollmentRepository jpaEnrollmentRepository;

  @Override
  public Enrollment save(Enrollment enrollment) {
    return this.jpaEnrollmentRepository.save(enrollment);
  }

  @Override
  public Optional<Enrollment> findById(Long id) {
    return this.jpaEnrollmentRepository.findById(id);
  }

  @Override
  public List<Enrollment> findByStudentId(Long studentId) {
    return this.jpaEnrollmentRepository.findAllByStudentId(studentId);
  }

  @Override
  public List<Enrollment> findByStudentIdAndSemester(Long studentId, String semester) {
    return this.jpaEnrollmentRepository.findAllByStudentIdAndSemester(studentId, semester);
  }

  @Override
  public boolean existsByStudentIdAndCourseId(Long studentId, Long courseId) {
    return this.jpaEnrollmentRepository.existsByStudentIdAndCourseId(studentId, courseId);
  }

  @Override
  public List<Enrollment> findByCourseId(Long courseId) {
    return this.jpaEnrollmentRepository.findAllByCourseId(courseId);
  }

  @Override
  public void deleteById(Long id) {
    this.jpaEnrollmentRepository.deleteById(id);
  }

}
