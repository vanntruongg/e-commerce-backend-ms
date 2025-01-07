package com.vantruong.order.service;

import com.vantruong.order.constant.KafkaTopic;
import com.vantruong.order.dto.OrderCreateResponse;
import com.vantruong.order.viewmodel.OrderPostVm;
import com.vantruong.order.entity.Order;
import com.vantruong.order.entity.enumeration.PaymentMethod;
import com.vantruong.order.producer.KafkaProducer;
import com.vantruong.order.util.OrderConverter;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PaymentOrderService {
  OrderService orderService;
  VNPayService vnPayService;
  KafkaProducer kafkaProducer;
  OrderConverter orderConverter;

  @Transactional
  public OrderCreateResponse placeOrder(OrderPostVm orderRequest) {
    Order order = orderService.createNewOrder(orderRequest);

    String urlPayment = null;
    PaymentMethod paymentMethod = orderRequest.getPaymentMethod();

    if (paymentMethod.equals(PaymentMethod.VN_PAY)) {
      urlPayment = getPaymentUrl(order.getOrderId(), order.getTotalPrice());
    }
//    OrderEvent orderEvent = orderConverter.orderToKafka(order);
//    kafkaProducer.sendCreatedOrder(orderEvent);
    return OrderCreateResponse.builder()
            .paymentMethod(paymentMethod)
            .urlPayment(urlPayment)
            .build();
  }

  private String getPaymentUrl(Long orderId, Double amount) {
    return vnPayService.generatePaymentUrl(orderId, amount);
  }

  @KafkaListener(topics = KafkaTopic.PAYMENT)
  public void handlePaymentCallback(Map<String, String> params) {
    Boolean paymentResponse = vnPayService.handleVNPayCallBack(params);

    String orderId = params.get("vnp_OrderInfo").split(":")[1].trim();
    Order order = orderService.findById(Long.parseLong(orderId));

//    OrderEvent orderEvent = orderConverter.orderToKafka(order);
    if (paymentResponse) {
//      orderEvent.setOrderEventStatus(OrderEventStatus.NEW);
//      kafkaProducer.sendPaymentSuccess(orderEvent);
    } else {
//      compensateOrder(orderEvent);
//      orderEvent.setOrderEventStatus(OrderEventStatus.ROLLBACK);
//      CancelOrderEvent cancelOrderEvent = createCancelOrderEvent(order);
//      kafkaProducer.sendCancelOrder(cancelOrderEvent);
    }
  }

//  @KafkaListener(topics = KafkaTopics.ORDER_TOPIC)
//  public void compensateOrder(OrderEvent orderEvent) {
//    orderService.deleteOrder(orderEvent.getOrderId());
//  }
//
//
//  private CancelOrderEvent createCancelOrderEvent(Order order) {
//    Set<CancelOrderItem> cancelOrderItems = order.getOrderItems().stream()
//            .map(item -> new CancelOrderItem(
//                    item.getProductId(),
//                    item.getQuantity(),
//                    item.getProductSize()
//            ))
//            .collect(Collectors.toSet());
//    return CancelOrderEvent.builder()
//            .orderItems(cancelOrderItems)
//            .build();
//  }
}
