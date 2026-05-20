package com.hyk.univ.common.exception;

import lombok.Getter;

public class BusinessException extends RuntimeException {

  @Getter
  private final ErrorCode errorCode;

  public BusinessException(ErrorCode errorCode) {
    super(errorCode.getCode());
    this.errorCode = errorCode;
  }

  public BusinessException(ErrorCode errorCode, String message) {
    super(message);
    this.errorCode = errorCode;
  }

}
