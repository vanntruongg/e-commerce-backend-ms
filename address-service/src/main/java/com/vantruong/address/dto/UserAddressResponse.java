package com.vantruong.address.dto;

public record UserAddressResponse(
        int id,
        String name,
        String phone,
        String street,
        AddressDetail province,
        AddressDetail district,
        AddressDetail ward,
        boolean isDefault
) {
}
