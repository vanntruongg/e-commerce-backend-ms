package com.vantruong.common.dto.order;

public record OrderItemCommonDto(int orderDetailId,
                                 Long productId,
                                 int quantity,
                                 String productName,
                                 Double productPrice,
                                 String productImage,
                                 String productSize
) {
}
