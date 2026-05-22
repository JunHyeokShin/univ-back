package com.hyk.univ.enrollment.infrastructure;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hyk.univ.enrollment.domain.Enrollment;

public interface JpaEnrollmentRepository extends JpaRepository<Enrollment, Long> {

  List<Enrollment> findAllByStudentId(Long studentId);

  List<Enrollment> findAllByStudentIdAndSemester(Long studentId, String semester);

  boolean existsByStudentIdAndCourseId(Long studentId, Long courseId);

  List<Enrollment> findAllByCourseId(Long courseId);

}
