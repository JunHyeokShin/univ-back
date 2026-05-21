package com.hyk.univ.schedule.application;

import java.lang.reflect.Method;

import lombok.RequiredArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import com.hyk.univ.common.exception.BusinessException;
import com.hyk.univ.common.exception.ErrorCode;
import com.hyk.univ.schedule.domain.AcademicMode;

@RequiredArgsConstructor
@Aspect
@Component
public class ModeCheckAspect {

  private final ScheduleService scheduleService;

  @Before("@annotation(com.hyk.univ.schedule.application.RequireMode)")
  public void check(JoinPoint joinPoint) {
    MethodSignature signature = (MethodSignature) joinPoint.getSignature();
    Method method = signature.getMethod();
    RequireMode annotation = method.getAnnotation(RequireMode.class);
    AcademicMode required = annotation.value();
    AcademicMode current = this.scheduleService.currentMode();
    if (current != required) {
      throw new BusinessException(ErrorCode.WRONG_ACADEMIC_MODE,
          "current mode is " + current + ", required " + required);
    }
  }

}
