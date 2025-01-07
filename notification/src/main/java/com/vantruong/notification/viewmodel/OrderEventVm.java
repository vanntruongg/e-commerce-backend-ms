package com.vantruong.notification.viewmodel;

import java.util.Set;

public record OrderEventVm(
        Long orderId,
        String email,
        String notes,
        Double totalPrice,
        String orderStatus,
        String orderEventStatus,
        String paymentStatus,
        String name,
        String phone,
        String address,
        Set<OrderItemVm> orderItems
) {
}
