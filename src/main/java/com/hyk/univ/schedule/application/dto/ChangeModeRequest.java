package com.hyk.univ.schedule.application.dto;

import jakarta.validation.constraints.NotNull;

import com.hyk.univ.schedule.domain.AcademicMode;

public record ChangeModeRequest(
    @NotNull AcademicMode mode
) {

}
