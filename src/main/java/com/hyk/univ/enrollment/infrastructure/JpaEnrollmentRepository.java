package com.hyk.univ.enrollment.infrastructure;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hyk.univ.enrollment.domain.Enrollment;

public interface JpaEnrollmentRepository extends JpaRepository<Enrollment, Long> {

  List<Enrollment> findByStudentIdAndSemester(Long studentId, String semester);

  List<Enrollment> findByCourseId(Long courseId);

  List<Enrollment> findByStudentId(Long studentId);

  boolean existsByStudentIdAndCourseId(Long studentId, Long courseId);

}
