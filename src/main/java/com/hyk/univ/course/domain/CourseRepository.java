package com.hyk.univ.course.domain;

import java.util.List;
import java.util.Optional;

public interface CourseRepository {

  Course save(Course course);

  Optional<Course> findById(Long id);

  List<Course> findAllOpen();

  List<Course> findByType(CourseType type);

  List<Course> findByProfessorId(Long professorId);

  void deleteById(Long id);

}
