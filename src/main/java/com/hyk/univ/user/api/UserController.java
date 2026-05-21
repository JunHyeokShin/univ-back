package com.hyk.univ.user.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hyk.univ.common.security.AuthUser;
import com.hyk.univ.user.application.UserService;
import com.hyk.univ.user.application.dto.UpdateMyInfoRequest;
import com.hyk.univ.user.application.dto.UserResponse;

@RequiredArgsConstructor
@RequestMapping("/api/users")
@RestController
public class UserController {

  private final UserService userService;

  @GetMapping("/me")
  public ResponseEntity<UserResponse> getMe(@AuthenticationPrincipal AuthUser user) {
    return ResponseEntity.ok(this.userService.getMe(user.userId()));
  }

  @PatchMapping("/me")
  public ResponseEntity<Void> updateMe(@AuthenticationPrincipal AuthUser user,
      @RequestBody UpdateMyInfoRequest request) {
    this.userService.updateMyInfo(user.userId(), request);
    return ResponseEntity.noContent().build();
  }

}
