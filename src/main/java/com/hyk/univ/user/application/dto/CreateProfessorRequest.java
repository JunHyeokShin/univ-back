package com.hyk.univ.user.application.dto;

import jakarta.validation.constraints.NotBlank;

public record CreateProfessorRequest(
    @NotBlank String professorNumber,
    @NotBlank String password,
    @NotBlank String name,
    String contact,
    @NotBlank String department
) {

}
