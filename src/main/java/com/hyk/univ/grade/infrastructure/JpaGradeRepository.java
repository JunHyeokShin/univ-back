package com.hyk.univ.grade.infrastructure;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hyk.univ.grade.domain.Grade;

public interface JpaGradeRepository extends JpaRepository<Grade, Long> {

  Optional<Grade> findByStudentIdAndCourseId(Long studentId, Long courseId);

  List<Grade> findByStudentId(Long studentId);

  List<Grade> findByStudentIdAndSemester(Long studentId, String semester);

  List<Grade> findByCourseId(Long courseId);

}
