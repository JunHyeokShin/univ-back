package com.hyk.univ.course.infrastructure;

import java.util.List;
import java.util.Optional;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import com.hyk.univ.course.domain.Course;
import com.hyk.univ.course.domain.CourseRepository;
import com.hyk.univ.course.domain.CourseStatus;
import com.hyk.univ.course.domain.CourseType;

@RequiredArgsConstructor
@Repository
public class CourseRepositoryImpl implements CourseRepository {

  private final JpaCourseRepository jpaCourseRepository;

  @Override
  public Course save(Course course) {
    return this.jpaCourseRepository.save(course);
  }

  @Override
  public Optional<Course> findById(Long id) {
    return this.jpaCourseRepository.findById(id);
  }

  @Override
  public List<Course> findAllOpen() {
    return this.jpaCourseRepository.findAllByStatus(CourseStatus.OPEN);
  }

  @Override
  public List<Course> findByType(CourseType type) {
    return this.jpaCourseRepository.findAllByTypeAndStatus(type, CourseStatus.OPEN);
  }

  @Override
  public List<Course> findByProfessorId(Long professorId) {
    return this.jpaCourseRepository.findAllByProfessorId(professorId);
  }

  @Override
  public void deleteById(Long id) {
    this.jpaCourseRepository.deleteById(id);
  }

}
