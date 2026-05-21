package com.hyk.univ.schedule.api;

import java.util.Map;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hyk.univ.schedule.application.ScheduleService;
import com.hyk.univ.schedule.application.dto.ChangeModeRequest;
import com.hyk.univ.schedule.domain.AcademicMode;

@RequiredArgsConstructor
@RequestMapping("/api/schedule")
@RestController
public class ScheduleController {

  private final ScheduleService scheduleService;

  @GetMapping("/mode")
  public ResponseEntity<Map<String, AcademicMode>> currentMode() {
    return ResponseEntity.ok(Map.of("mode", this.scheduleService.currentMode()));
  }

  @PreAuthorize("hasRole('ADMIN')")
  @PutMapping("/mode")
  public ResponseEntity<Void> changeMode(@Valid @RequestBody ChangeModeRequest request) {
    this.scheduleService.changeMode(request.mode());
    return ResponseEntity.noContent().build();
  }

}
