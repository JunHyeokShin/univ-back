package com.hyk.univ.enrollment.application.dto;

import jakarta.validation.constraints.NotNull;

public record EnrollRequest(
    @NotNull Long courseId
) {

}
