package com.hyk.univ.grade.domain;

import org.springframework.stereotype.Component;

@Component
public class GradeCalculator {

  public LetterGrade calculate(Score score, Attendance attendance) {
    if (attendance.exceedsAbsenceLimit()) {
      return LetterGrade.F;
    }
    int total = score.total();
    if (total < 60) {
      return LetterGrade.F;
    }
    if (total >= 95) {
      return LetterGrade.A_PLUS;
    }
    if (total >= 90) {
      return LetterGrade.A;
    }
    if (total >= 85) {
      return LetterGrade.B_PLUS;
    }
    if (total >= 80) {
      return LetterGrade.B;
    }
    if (total >= 75) {
      return LetterGrade.C_PLUS;
    }
    if (total >= 70) {
      return LetterGrade.C;
    }
    if (total >= 65) {
      return LetterGrade.D_PLUS;
    }
    return LetterGrade.D;
  }

}
