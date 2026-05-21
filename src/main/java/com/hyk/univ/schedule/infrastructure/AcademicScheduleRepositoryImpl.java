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
  public AcademicSchedule save(AcademicSchedule schedule) {
    return this.jpaAcademicScheduleRepository.save(schedule);
  }

  @Override
  public AcademicSchedule getCurrent() {
    return this.jpaAcademicScheduleRepository.findById(1L)
        .orElseThrow(() -> new IllegalStateException("학사 일정이 초기화되지 않았습니다."));
  }

  @Override
  public boolean existsById(Long id) {
    return this.jpaAcademicScheduleRepository.existsById(id);
  }

}
