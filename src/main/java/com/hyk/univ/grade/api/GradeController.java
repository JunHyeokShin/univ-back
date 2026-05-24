package com.hyk.univ.grade.api;

import java.util.List;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hyk.univ.common.security.AuthUser;
import com.hyk.univ.grade.application.GradeService;
import com.hyk.univ.grade.application.dto.GradeResponse;
import com.hyk.univ.grade.application.dto.InputGradeRequest;
import com.hyk.univ.grade.application.dto.UpdateAttendanceRequest;

@RequiredArgsConstructor
@RequestMapping("/api/grades")
@RestController
public class GradeController {

  private final GradeService gradeService;

  @GetMapping("/me")
  @PreAuthorize("hasRole('STUDENT')")
  public ResponseEntity<List<GradeResponse>> myGrades(
      @AuthenticationPrincipal AuthUser user,
      @RequestParam(required = false) String semester
  ) {
    if (semester == null) {
      return ResponseEntity.ok(this.gradeService.findMyGrades(user.userId()));
    }
    return ResponseEntity.ok(this.gradeService.findMyGradesBySemester(user.userId(), semester));
  }

  @GetMapping("/courses/{courseId}")
  @PreAuthorize("hasRole('PROFESSOR')")
  public ResponseEntity<List<GradeResponse>> gradesOfCourse(
      @AuthenticationPrincipal AuthUser user,
      @PathVariable Long courseId
  ) {
    return ResponseEntity.ok(this.gradeService.findByCourse(user.userId(), courseId));
  }

  @PostMapping("/scores")
  @PreAuthorize("hasRole('PROFESSOR')")
  public ResponseEntity<GradeResponse> inputGrade(
      @AuthenticationPrincipal AuthUser user,
      @Valid @RequestBody InputGradeRequest request
  ) {
    return ResponseEntity.ok(this.gradeService.inputGrade(user.userId(), request));
  }

  @PostMapping("/attendance")
  @PreAuthorize("hasRole('PROFESSOR')")
  public ResponseEntity<GradeResponse> updateAttendance(
      @AuthenticationPrincipal AuthUser user,
      @Valid @RequestBody UpdateAttendanceRequest request
  ) {
    return ResponseEntity.ok(this.gradeService.updateAttendance(user.userId(), request));
  }

}
