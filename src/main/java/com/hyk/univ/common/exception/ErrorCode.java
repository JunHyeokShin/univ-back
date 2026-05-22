package com.hyk.univ.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

  INVALID_CREDENTIALS(HttpStatus.UNAUTHORIZED, "INVALID_CREDENTIALS"),
  USER_NOT_FOUND(HttpStatus.NOT_FOUND, "USER_NOT_FOUND"),
  COURSE_NOT_FOUND(HttpStatus.NOT_FOUND, "COURSE_NOT_FOUND"),
  COURSE_FULL(HttpStatus.CONFLICT, "COURSE_FULL"),
  COURSE_CLOSED(HttpStatus.CONFLICT, "COURSE_CLOSED"),
  CANNOT_CLOSE_COURSE(HttpStatus.CONFLICT, "CANNOT_CLOSE_COURSE"),
  USER_ALREADY_EXISTS(HttpStatus.CONFLICT, "USER_ALREADY_EXISTS"),
  WRONG_ACADEMIC_MODE(HttpStatus.CONFLICT, "WRONG_ACADEMIC_MODE"),
  ENROLLMENT_ALREADY_EXISTS(HttpStatus.CONFLICT, "ENROLLMENT_ALREADY_EXISTS"),
  MAX_CREDIT_EXCEEDED(HttpStatus.CONFLICT, "MAX_CREDIT_EXCEEDED"),
  ENROLLMENT_NOT_FOUND(HttpStatus.NOT_FOUND, "ENROLLMENT_NOT_FOUND");

  private final HttpStatus status;
  private final String code;

}
