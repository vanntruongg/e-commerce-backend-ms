package com.vantruong.order.dto;

public record OrderStatsResponse(
        int month,
        int totalOrders
) {
}
