package com.hyk.univ.course.application;

import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hyk.univ.common.exception.BusinessException;
import com.hyk.univ.common.exception.ErrorCode;
import com.hyk.univ.course.application.dto.CourseResponse;
import com.hyk.univ.course.application.dto.CreateCourseRequest;
import com.hyk.univ.course.domain.Course;
import com.hyk.univ.course.domain.CourseRepository;
import com.hyk.univ.course.domain.CourseType;
import com.hyk.univ.user.domain.Role;
import com.hyk.univ.user.domain.UserRepository;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class CourseService {

  private final CourseRepository courseRepository;
  private final UserRepository userRepository;

  public List<CourseResponse> findAllOpen(CourseType type) {
    List<Course> courses = (type == null)
        ? this.courseRepository.findAllOpen()
        : this.courseRepository.findByType(type);
    return courses.stream().map(CourseResponse::from).toList();
  }

  public CourseResponse findById(Long id) {
    return this.courseRepository.findById(id).map(CourseResponse::from)
        .orElseThrow(() -> new BusinessException(ErrorCode.COURSE_NOT_FOUND));
  }

  public List<CourseResponse> findByProfessor(Long professorId) {
    return this.courseRepository.findByProfessorId(professorId).stream()
        .map(CourseResponse::from).toList();
  }

  @Transactional
  public CourseResponse openCourse(CreateCourseRequest request) {
    this.userRepository.findById(request.professorId())
        .filter(user -> user.getRole() == Role.PROFESSOR)
        .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND,
            "존재하지 않는 교수 ID: " + request.professorId()));
    Course course = Course.open(request.name(), request.type(), request.credit(),
        request.capacity(), request.professorId(), request.semester());
    return CourseResponse.from(this.courseRepository.save(course));
  }

  @Transactional
  public void closeCourse(Long courseId) {
    Course course = this.courseRepository.findById(courseId)
        .orElseThrow(() -> new BusinessException(ErrorCode.COURSE_NOT_FOUND));
    course.close();
  }

}
