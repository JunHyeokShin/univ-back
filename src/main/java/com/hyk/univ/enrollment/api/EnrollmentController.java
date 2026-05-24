package com.hyk.univ.enrollment.api;

import java.util.List;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hyk.univ.common.security.AuthUser;
import com.hyk.univ.enrollment.application.EnrollmentService;
import com.hyk.univ.enrollment.application.dto.EnrollRequest;
import com.hyk.univ.enrollment.application.dto.EnrollmentResponse;

@RequiredArgsConstructor
@RequestMapping("/api/enrollments")
@RestController
public class EnrollmentController {

  private final EnrollmentService enrollmentService;

  @PreAuthorize("hasRole('STUDENT')")
  @GetMapping("/me")
  public ResponseEntity<List<EnrollmentResponse>> myEnrollments(@AuthenticationPrincipal AuthUser user) {
    return ResponseEntity.ok(this.enrollmentService.myEnrollments(user.userId()));
  }

  @PreAuthorize("hasAnyRole('PROFESSOR', 'ADMIN')")
  @GetMapping("/courses/{courseId}")
  public ResponseEntity<List<EnrollmentResponse>> studentsByCourse(@PathVariable Long courseId) {
    return ResponseEntity.ok(this.enrollmentService.studentsOfCourse(courseId));
  }

  @PreAuthorize("hasRole('STUDENT')")
  @PostMapping
  public ResponseEntity<EnrollmentResponse> enroll(@AuthenticationPrincipal AuthUser user,
      @Valid @RequestBody EnrollRequest request) {
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(this.enrollmentService.enroll(user.userId(), request.courseId()));
  }

  @PreAuthorize("hasRole('STUDENT')")
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> cancel(@AuthenticationPrincipal AuthUser user, @PathVariable Long id) {
    this.enrollmentService.cancel(user.userId(), id);
    return ResponseEntity.noContent().build();
  }

}
