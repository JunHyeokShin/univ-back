package com.hyk.univ.course.api;

import java.util.List;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hyk.univ.course.application.CourseService;
import com.hyk.univ.course.application.dto.CourseResponse;
import com.hyk.univ.course.application.dto.CreateCourseRequest;
import com.hyk.univ.course.domain.CourseType;

@RequiredArgsConstructor
@RequestMapping("/api/courses")
@RestController
public class CourseController {

  private final CourseService courseService;

  @GetMapping
  public ResponseEntity<List<CourseResponse>> list(@RequestParam(required = false) CourseType type) {
    return ResponseEntity.ok(this.courseService.findAllOpen(type));
  }

  @PreAuthorize("hasRole('ADMIN')")
  @PostMapping
  public ResponseEntity<CourseResponse> open(@Valid @RequestBody CreateCourseRequest request) {
    return ResponseEntity.status(HttpStatus.CREATED).body(this.courseService.openCourse(request));
  }

}
