package com.hyk.univ.enrollment.domain;

import java.util.List;
import java.util.Optional;

public interface EnrollmentRepository {

  Enrollment save(Enrollment enrollment);

  Optional<Enrollment> findById(Long id);

  List<Enrollment> findByStudentIdAndSemester(Long studentId, String semester);

  List<Enrollment> findByCourseId(Long courseId);

  List<Enrollment> findByStudentId(Long studentId);

  boolean existsByStudentIdAndCourseId(Long studentId, Long courseId);

  void deleteById(Long id);

}
