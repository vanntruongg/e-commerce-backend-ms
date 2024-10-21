package com.vantruong.identity.dto;

import java.util.List;

public record UserListDto(
        List<UserDto> userList,
        int totalElements,
        int totalPages,
        boolean isLast
) {
}
