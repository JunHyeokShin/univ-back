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
public class Score {

  private int attendanceScore;
  private int assignmentScore;
  private int midtermScore;
  private int finalScore;

  public int total() {
    return (int) Math.round(
        this.attendanceScore * 0.1 + this.assignmentScore * 0.2 + this.midtermScore * 0.35 + this.finalScore * 0.35
    );
  }

}
