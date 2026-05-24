package com.hyk.univ.grade.application.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record InputGradeRequest(
    @NotNull Long studentId,
    @NotNull Long courseId,
    @Min(0) @Max(100) int attendanceScore,
    @Min(0) @Max(100) int assignmentScore,
    @Min(0) @Max(100) int midtermScore,
    @Min(0) @Max(100) int finalScore
) {

}
