package com.hyk.univ.enrollment.domain;

import java.util.List;
import java.util.Optional;

public interface EnrollmentRepository {

  Enrollment save(Enrollment enrollment);

  Optional<Enrollment> findById(Long id);

  List<Enrollment> findByStudentId(Long studentId);

  List<Enrollment> findByStudentIdAndSemester(Long studentId, String semester);

  boolean existsByStudentIdAndCourseId(Long studentId, Long courseId);

  List<Enrollment> findByCourseId(Long courseId);

  void deleteById(Long id);

}
