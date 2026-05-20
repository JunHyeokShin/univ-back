package com.hyk.univ.common.exception;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(BusinessException.class)
  public ResponseEntity<?> handleBusiness(BusinessException ex) {
    return ResponseEntity.status(ex.getErrorCode().getStatus())
        .body(Map.of("code", ex.getErrorCode().getCode(), "message", ex.getMessage()));
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<?> handleValidation(MethodArgumentNotValidException ex) {
    String msg = ex.getBindingResult().getFieldErrors().stream()
        .findFirst()
        .map(f -> f.getField() + ": " + f.getDefaultMessage())
        .orElse("invalid input");
    return ResponseEntity.badRequest().body(Map.of("code", "INVALID_INPUT", "message", msg));
  }

  @ExceptionHandler(AccessDeniedException.class)
  public ResponseEntity<?> handleAccessDenied(AccessDeniedException ex) {
    return ResponseEntity.status(HttpStatus.FORBIDDEN)
        .body(Map.of("code", "FORBIDDEN", "message", "access denied"));
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<?> handleEtc(Exception ex) {
    return ResponseEntity.internalServerError()
        .body(Map.of("code", "INTERNAL_ERROR", "message", ex.getMessage()));
  }

}
