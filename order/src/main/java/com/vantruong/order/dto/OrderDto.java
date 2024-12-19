package com.vantruong.order.dto;

import com.vantruong.order.entity.enumeration.PaymentMethod;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.Set;

@Builder
public record OrderDto(
        Long orderId,
        String email,
        String name,
        String phone,
        String address,
        String notes,
        double totalPrice,
        String orderStatus,
        String paymentStatus,
        PaymentMethod paymentMethod,
        String created,
        LocalDateTime createdDate,
        Set<OrderItemDto> orderItems
) {
}
