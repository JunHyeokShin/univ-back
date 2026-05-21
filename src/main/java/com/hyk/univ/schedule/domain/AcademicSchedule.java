package com.hyk.univ.schedule.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "academic_schedule")
@Entity
public class AcademicSchedule {

  @Id
  private Long id = 1L;

  @Column(nullable = false)
  @Enumerated(EnumType.STRING)
  private AcademicMode currentMode;

  public static AcademicSchedule initial() {
    AcademicSchedule schedule = new AcademicSchedule();
    schedule.currentMode = AcademicMode.VIEWING;
    return schedule;
  }

  public void changeMode(AcademicMode mode) {
    this.currentMode = mode;
  }

}
