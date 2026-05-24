package com.hyk.univ.grade.domain;

import java.util.List;
import java.util.Optional;

public interface GradeRepository {

  Grade save(Grade grade);

  Optional<Grade> findById(Long id);

  Optional<Grade> findByStudentIdAndCourseId(Long studentId, Long courseId);

  List<Grade> findByStudentId(Long studentId);

  List<Grade> findByStudentIdAndSemester(Long studentId, String semester);

  List<Grade> findByCourseId(Long courseId);

}
