package com.hyk.univ.schedule.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hyk.univ.schedule.domain.AcademicSchedule;

public interface JpaAcademicScheduleRepository extends JpaRepository<AcademicSchedule, Long> {

}
