package com.hyk.univ.schedule.infrastructure;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import com.hyk.univ.schedule.domain.AcademicSchedule;
import com.hyk.univ.schedule.domain.AcademicScheduleRepository;

@RequiredArgsConstructor
@Repository
public class AcademicScheduleRepositoryImpl implements AcademicScheduleRepository {

  private final JpaAcademicScheduleRepository jpaAcademicScheduleRepository;

  @Override
  public AcademicSchedule getCurrent() {
    return this.jpaAcademicScheduleRepository.findById(1L)
        .orElseGet(() -> this.jpaAcademicScheduleRepository.save(AcademicSchedule.initial()));
  }

  @Override
  public AcademicSchedule save(AcademicSchedule schedule) {
    return this.jpaAcademicScheduleRepository.save(schedule);
  }

}
