package com.hyk.univ.schedule.domain;

public interface AcademicScheduleRepository {

  AcademicSchedule getCurrent();

  AcademicSchedule save(AcademicSchedule schedule);

}
