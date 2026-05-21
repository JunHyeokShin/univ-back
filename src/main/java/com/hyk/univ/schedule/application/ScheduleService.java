package com.hyk.univ.schedule.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hyk.univ.schedule.domain.AcademicMode;
import com.hyk.univ.schedule.domain.AcademicSchedule;
import com.hyk.univ.schedule.domain.AcademicScheduleRepository;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class ScheduleService {

  private final AcademicScheduleRepository academicScheduleRepository;

  public AcademicMode currentMode() {
    return this.academicScheduleRepository.getCurrent().getCurrentMode();
  }

  @Transactional
  public void changeMode(AcademicMode mode) {
    AcademicSchedule schedule = this.academicScheduleRepository.getCurrent();
    schedule.changeMode(mode);
  }

}
