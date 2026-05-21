package com.hyk.univ.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

  INVALID_CREDENTIALS(HttpStatus.UNAUTHORIZED, "INVALID_CREDENTIALS"),
  USER_NOT_FOUND(HttpStatus.NOT_FOUND, "USER_NOT_FOUND"),
  COURSE_FULL(HttpStatus.CONFLICT, "COURSE_FULL"),
  COURSE_CLOSED(HttpStatus.CONFLICT, "COURSE_CLOSED"),
  CANNOT_CLOSE_COURSE(HttpStatus.CONFLICT, "CANNOT_CLOSE_COURSE"),
  USER_ALREADY_EXISTS(HttpStatus.CONFLICT, "USER_ALREADY_EXISTS");

  private final HttpStatus status;
  private final String code;

}
