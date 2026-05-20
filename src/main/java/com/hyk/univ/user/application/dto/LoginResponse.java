package com.hyk.univ.user.application.dto;

public record LoginResponse(
    String token,
    String role,
    Long userId,
    String name
) {

}
