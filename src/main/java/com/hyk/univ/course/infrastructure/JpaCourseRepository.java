package com.hyk.univ.course.infrastructure;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hyk.univ.course.domain.Course;
import com.hyk.univ.course.domain.CourseStatus;
import com.hyk.univ.course.domain.CourseType;

public interface JpaCourseRepository extends JpaRepository<Course, Long> {

  List<Course> findAllByStatus(CourseStatus status);

  List<Course> findAllByTypeAndStatus(CourseType type, CourseStatus status);

  List<Course> findAllByProfessorId(Long professorId);

}
