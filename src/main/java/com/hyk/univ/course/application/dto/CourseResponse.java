package com.hyk.univ.course.application.dto;

import com.hyk.univ.course.domain.Course;
import com.hyk.univ.course.domain.CourseStatus;
import com.hyk.univ.course.domain.CourseType;

public record CourseResponse(
    Long id,
    String name,
    CourseType type,
    int credit,
    int capacity,
    int currentEnrollment,
    Long professorId,
    String semester,
    CourseStatus status
) {

  public static CourseResponse from(Course course) {
    return new CourseResponse(
        course.getId(),
        course.getName(),
        course.getType(),
        course.getCredit(),
        course.getCapacity(),
        course.getCurrentEnrollment(),
        course.getProfessorId(),
        course.getSemester(),
        course.getStatus()
    );
  }

}
