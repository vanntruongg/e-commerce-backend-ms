package com.vantruong.address.dto;

public record AddressDetail(
        int id,
        String name,
        String type,
        String code,
        String parentCode
) {
}
