package com.hyk.univ.course.api;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hyk.univ.course.application.CourseService;
import com.hyk.univ.course.application.dto.CourseResponse;
import com.hyk.univ.course.application.dto.CreateCourseRequest;

@RequiredArgsConstructor
@RequestMapping("/api/courses")
@RestController
public class CourseController {

  private final CourseService courseService;

  @PreAuthorize("hasRole('ADMIN')")
  @PostMapping
  public ResponseEntity<CourseResponse> open(@Valid @RequestBody CreateCourseRequest request) {
    return ResponseEntity.status(HttpStatus.CREATED).body(this.courseService.openCourse(request));
  }

}
