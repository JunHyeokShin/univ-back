package com.hyk.univ.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

  INVALID_CREDENTIALS(HttpStatus.UNAUTHORIZED, "INVALID_CREDENTIALS"),
  USER_ALREADY_EXISTS(HttpStatus.CONFLICT, "USER_ALREADY_EXISTS");

  private final HttpStatus status;
  private final String code;

}
