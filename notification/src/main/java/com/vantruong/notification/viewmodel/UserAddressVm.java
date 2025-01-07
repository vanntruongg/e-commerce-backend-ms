package com.vantruong.notification.viewmodel;

public record UserAddressVm(
        Integer id,
        String name,
        String phone,
        String street,
        String ward,
        String district,
        String province
) {
}
