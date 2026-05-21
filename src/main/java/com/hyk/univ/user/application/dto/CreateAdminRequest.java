package com.hyk.univ.user.application.dto;

import jakarta.validation.constraints.NotBlank;

public record CreateAdminRequest(
    @NotBlank String username,
    @NotBlank String password,
    @NotBlank String name,
    String contact
) {

}
