package com.hyk.univ.user.application.dto;

import jakarta.validation.constraints.NotBlank;

public record LoginRequest(
    @NotBlank String loginId,
    @NotBlank String password
) {

}
