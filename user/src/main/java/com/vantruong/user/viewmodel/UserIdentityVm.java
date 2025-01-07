package com.vantruong.user.viewmodel;

import java.util.Set;

public record UserIdentityVm(
        String email,
        String password,
        Boolean isActive,
        Set<String> roles
) {
}
