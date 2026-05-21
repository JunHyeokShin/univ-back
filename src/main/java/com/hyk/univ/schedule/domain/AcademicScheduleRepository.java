package com.hyk.univ.schedule.domain;

public interface AcademicScheduleRepository {

  AcademicSchedule save(AcademicSchedule schedule);

  AcademicSchedule getCurrent();

  boolean existsById(Long id);

}
