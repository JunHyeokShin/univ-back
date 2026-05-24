package com.hyk.univ.grade.infrastructure;

import java.util.List;
import java.util.Optional;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import com.hyk.univ.grade.domain.Grade;
import com.hyk.univ.grade.domain.GradeRepository;

@RequiredArgsConstructor
@Repository
public class GradeRepositoryImpl implements GradeRepository {

  private final JpaGradeRepository jpaGradeRepository;

  @Override
  public Grade save(Grade grade) {
    return this.jpaGradeRepository.save(grade);
  }

  @Override
  public Optional<Grade> findById(Long id) {
    return this.jpaGradeRepository.findById(id);
  }

  @Override
  public Optional<Grade> findByStudentIdAndCourseId(Long studentId, Long courseId) {
    return this.jpaGradeRepository.findByStudentIdAndCourseId(studentId, courseId);
  }

  @Override
  public List<Grade> findByStudentId(Long studentId) {
    return this.jpaGradeRepository.findByStudentId(studentId);
  }

  @Override
  public List<Grade> findByStudentIdAndSemester(Long studentId, String semester) {
    return this.jpaGradeRepository.findByStudentIdAndSemester(studentId, semester);
  }

  @Override
  public List<Grade> findByCourseId(Long courseId) {
    return this.jpaGradeRepository.findByCourseId(courseId);
  }

}
