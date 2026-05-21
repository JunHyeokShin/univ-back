package com.hyk.univ.user.application;

import java.time.Year;
import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hyk.univ.common.exception.BusinessException;
import com.hyk.univ.common.exception.ErrorCode;
import com.hyk.univ.user.application.dto.CreateAdminRequest;
import com.hyk.univ.user.application.dto.CreateProfessorRequest;
import com.hyk.univ.user.application.dto.CreateStudentRequest;
import com.hyk.univ.user.application.dto.UserResponse;
import com.hyk.univ.user.domain.User;
import com.hyk.univ.user.domain.UserRepository;

@RequiredArgsConstructor
@Transactional
@Service
public class AccountAdminService {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

  public List<UserResponse> findAll() {
    return this.userRepository.findAll().stream()
        .map(UserResponse::from)
        .toList();
  }

  public UserResponse createStudent(CreateStudentRequest request) {
    String studentNumber = generateNextStudentNumber();
    if (this.userRepository.existsByLoginId(studentNumber)) {
      throw new BusinessException(ErrorCode.USER_ALREADY_EXISTS);
    }
    User user = User.createStudent(studentNumber, this.passwordEncoder.encode(request.password()),
        request.name(), request.contact(), request.department());
    return UserResponse.from(this.userRepository.save(user));
  }

  public UserResponse createProfessor(CreateProfessorRequest request) {
    if (this.userRepository.existsByLoginId(request.professorNumber())) {
      throw new BusinessException(ErrorCode.USER_ALREADY_EXISTS);
    }
    User user = User.createProfessor(request.professorNumber(), this.passwordEncoder.encode(request.password()),
        request.name(), request.contact(), request.department());
    return UserResponse.from(this.userRepository.save(user));
  }

  public UserResponse createAdmin(CreateAdminRequest request) {
    if (this.userRepository.existsByLoginId(request.username())) {
      throw new BusinessException(ErrorCode.USER_ALREADY_EXISTS);
    }
    User user = User.createAdmin(request.username(), this.passwordEncoder.encode(request.password()),
        request.name(), request.contact());
    return UserResponse.from(this.userRepository.save(user));
  }

  private String generateNextStudentNumber() {
    String yearPrefix = String.valueOf(Year.now().getValue());
    String currentMax = this.userRepository.findMaxStudentNumberByYearPrefix(yearPrefix);
    int next = 1;
    if (currentMax != null && currentMax.length() == 8) {
      next = Integer.parseInt(currentMax.substring(4)) + 1;
    }
    return yearPrefix + String.format("%04d", next);
  }

}
