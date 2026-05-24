package com.hyk.univ.enrollment.domain;

import org.springframework.stereotype.Component;

import com.hyk.univ.common.exception.BusinessException;
import com.hyk.univ.common.exception.ErrorCode;

@Component
public class EnrollmentPolicy {

  public static final int MAX_CREDIT_PER_SEMESTER = 18;

  public void checkCreditLimit(int currentTotalCredit, int additionalCredit) {
    if (currentTotalCredit + additionalCredit > MAX_CREDIT_PER_SEMESTER) {
      throw new BusinessException(ErrorCode.CREDIT_LIMIT_EXCEEDED,
          "current=" + currentTotalCredit + ", adding=" + additionalCredit + ", limit=" + MAX_CREDIT_PER_SEMESTER);
    }
  }

  public void checkDuplicate(boolean alreadyEnrolled) {
    if (alreadyEnrolled) {
      throw new BusinessException(ErrorCode.DUPLICATE_ENROLLMENT);
    }
  }

}
