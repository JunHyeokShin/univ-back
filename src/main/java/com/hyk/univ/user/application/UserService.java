package com.hyk.univ.user.application;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hyk.univ.common.exception.BusinessException;
import com.hyk.univ.common.exception.ErrorCode;
import com.hyk.univ.user.application.dto.UpdateMyInfoRequest;
import com.hyk.univ.user.application.dto.UserResponse;
import com.hyk.univ.user.domain.User;
import com.hyk.univ.user.domain.UserRepository;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class UserService {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

  public UserResponse getMe(Long userId) {
    User user = this.userRepository.findById(userId)
        .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));
    return UserResponse.from(user);
  }

  @Transactional
  public void updateMyInfo(Long userId, UpdateMyInfoRequest request) {
    User user = this.userRepository.findById(userId)
        .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));
    if (request.password() != null && !request.password().isBlank()) {
      user.updatePassword(this.passwordEncoder.encode(request.password()));
    }
    if (request.contact() != null) {
      user.updateContact(request.contact());
    }
  }

}
