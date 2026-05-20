package com.hyk.univ.user.application;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.hyk.univ.common.exception.BusinessException;
import com.hyk.univ.common.exception.ErrorCode;
import com.hyk.univ.common.security.JwtProvider;
import com.hyk.univ.user.application.dto.LoginRequest;
import com.hyk.univ.user.application.dto.LoginResponse;
import com.hyk.univ.user.domain.User;
import com.hyk.univ.user.domain.UserRepository;

@RequiredArgsConstructor
@Service
public class AuthService {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final JwtProvider jwtProvider;

  public LoginResponse login(LoginRequest request) {
    User user = this.userRepository.findByLoginId(request.loginId())
        .orElseThrow(() -> new BusinessException(ErrorCode.INVALID_CREDENTIALS));
    if (!this.passwordEncoder.matches(request.password(), user.getPassword())) {
      throw new BusinessException(ErrorCode.INVALID_CREDENTIALS);
    }
    String token = this.jwtProvider.createToken(user.getId(), user.getRole());
    return new LoginResponse(token, user.getRole().name(), user.getId(), user.getName());
  }

}
