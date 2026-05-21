package com.hyk.univ.course.application.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import com.hyk.univ.course.domain.CourseType;

public record CreateCourseRequest(
    @NotBlank String name,
    @NotNull CourseType type,
    @Min(1) int credit,
    @Min(1) int capacity,
    @NotNull Long professorId,
    @NotBlank String semester
) {

}
