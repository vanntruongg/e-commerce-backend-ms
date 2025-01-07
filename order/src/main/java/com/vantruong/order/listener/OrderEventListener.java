package com.vantruong.order.listener;


import com.vantruong.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderEventListener {
  private final OrderService orderService;

  public static final String PAYMENT_SUCCESS = "payment-success";


  @KafkaListener(topics = PAYMENT_SUCCESS)
  public void handlePaymentSuccess() {
//    orderService.updatePaymentStatus(orderEvent.getOrderId(), PaymentStatus.COMPLETED);
  }
}
