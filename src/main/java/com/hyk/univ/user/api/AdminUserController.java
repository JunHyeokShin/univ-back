package com.hyk.univ.user.api;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hyk.univ.user.application.AccountAdminService;
import com.hyk.univ.user.application.dto.CreateStudentRequest;
import com.hyk.univ.user.application.dto.UserResponse;

@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
@RequestMapping("/api/admin/users")
@RestController
public class AdminUserController {

  private final AccountAdminService accountAdminService;

  @PostMapping("/students")
  public ResponseEntity<UserResponse> createStudent(@Valid @RequestBody CreateStudentRequest request) {
    return ResponseEntity.status(HttpStatus.CREATED).body(this.accountAdminService.createStudent(request));
  }

}
