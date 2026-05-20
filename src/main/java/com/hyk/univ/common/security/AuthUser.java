package com.hyk.univ.common.security;

import com.hyk.univ.user.domain.Role;

public record AuthUser(
    Long userId,
    Role role
) {

}
