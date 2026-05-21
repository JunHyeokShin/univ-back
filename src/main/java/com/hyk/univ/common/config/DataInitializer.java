package com.hyk.univ.common.config;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.hyk.univ.schedule.domain.AcademicSchedule;
import com.hyk.univ.schedule.domain.AcademicScheduleRepository;
import com.hyk.univ.user.domain.User;
import com.hyk.univ.user.domain.UserRepository;

@RequiredArgsConstructor
@Component
public class DataInitializer implements CommandLineRunner {

  private final UserRepository userRepository;
  private final AcademicScheduleRepository scheduleRepository;
  private final PasswordEncoder passwordEncoder;

  @Override
  public void run(String... args) throws Exception {
    if (!this.userRepository.existsByLoginId("admin")) {
      User admin = User.createAdmin(
          "admin",
          this.passwordEncoder.encode("admin1234"),
          "기본 관리자",
          "010-0000-0000"
      );
      this.userRepository.save(admin);
    }
    if (!this.scheduleRepository.existsById(1L)) {
      this.scheduleRepository.save(AcademicSchedule.initial());
    }
  }

}
