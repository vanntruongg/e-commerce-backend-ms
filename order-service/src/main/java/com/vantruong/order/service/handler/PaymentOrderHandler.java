package com.vantruong.order.service.handler;

import com.vantruong.order.dto.OrderCreateResponse;
import com.vantruong.order.dto.OrderRequest;

public interface PaymentOrderHandler {
  OrderCreateResponse placeOrder(OrderRequest orderRequest);
}
