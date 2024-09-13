package com.vantruong.common.dto.order;

import java.util.Set;

public record OrderCommonDto(Long orderId,
                             String email,
                             String name,
                             String phone,
                             String address,
                             String notes,
                             Double totalPrice,
                             String orderStatus,
                             String paymentMethod,
                             Set<OrderItemCommonDto> orderItems
) {
}
