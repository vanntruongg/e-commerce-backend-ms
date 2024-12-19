package com.vantruong.user.dto;

public record AddressDetail(
        int id,
        String name,
        String type,
        String code,
        String parentCode
) {
}
