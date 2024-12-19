package com.vantruong.notification.viewmodel;

public record SendMailVerifyUserRequest(
        String email,
        String name,
        String token
) {
}
