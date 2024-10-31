package com.vantruong.order.dto;

public record OrderStatsResponse(
        String month,
        int totalOrders
) {
}
