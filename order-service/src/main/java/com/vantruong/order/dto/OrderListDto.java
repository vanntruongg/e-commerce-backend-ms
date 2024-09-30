package com.vantruong.order.dto;

import java.util.List;

public record OrderListDto(
        List<OrderDto> orderList,
        int totalElements,
        int totalPages
) {
}
