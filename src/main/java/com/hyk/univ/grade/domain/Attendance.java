package com.hyk.univ.grade.domain;

import jakarta.persistence.Embeddable;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Embeddable
public class Attendance {

  private int presentCount;
  private int lateCount;
  private int absentCount;

  public static Attendance empty() {
    return new Attendance(0, 0, 0);
  }

  public boolean exceedsAbsenceLimit() {
    return this.absentCount >= 4;
  }

}
