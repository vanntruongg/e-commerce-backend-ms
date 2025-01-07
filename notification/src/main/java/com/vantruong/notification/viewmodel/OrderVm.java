package com.vantruong.notification.viewmodel;

import java.util.Set;

public record OrderVm(
        Long orderId,
        String email,
        String name,
        String phone,
        String address,
        String notes,
        Double totalPrice,
        String orderStatus,
        String paymentMethod,
        Set<OrderItemVm> orderItems
) {
}
