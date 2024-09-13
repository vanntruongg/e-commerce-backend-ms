package com.vantruong.address.dto;

public record UserAddressRequest(Integer id,
                                 String name,
                                 String phone,
                                 String street,
                                 Integer wardId,
                                 Integer districtId,
                                 Integer provinceId,
                                 Boolean isDefault
) {
}