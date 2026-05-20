package com.hyk.univ.user.api;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hyk.univ.user.application.AuthService;
import com.hyk.univ.user.application.dto.LoginRequest;
import com.hyk.univ.user.application.dto.LoginResponse;

@RequiredArgsConstructor
@RequestMapping("/api/auth")
@RestController
public class AuthController {

  private final AuthService authService;

  @PostMapping("/login")
  public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request) {
    return ResponseEntity.ok(this.authService.login(request));
  }

}
