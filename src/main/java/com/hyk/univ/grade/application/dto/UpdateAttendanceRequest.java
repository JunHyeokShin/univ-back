package com.hyk.univ.grade.application.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record UpdateAttendanceRequest(
    @NotNull Long studentId,
    @NotNull Long courseId,
    @Min(0) int presentCount,
    @Min(0) int lateCount,
    @Min(0) int absentCount
) {

}
