package com.hyk.univ.user.application.dto;

import jakarta.validation.constraints.NotBlank;

public record CreateStudentRequest(
    @NotBlank String password,
    @NotBlank String name,
    String contact,
    @NotBlank String department
) {

}
