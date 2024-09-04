package com.vantruong.order.service.handler;

import com.vantruong.common.constant.KafkaTopics;
import com.vantruong.common.event.OrderEvent;
import com.vantruong.common.event.OrderEventStatus;
import com.vantruong.order.dto.OrderCreateResponse;
import com.vantruong.order.dto.OrderRequest;
import com.vantruong.order.entity.DeliveryAddress;
import com.vantruong.order.entity.Order;
import com.vantruong.order.entity.PaymentMethod;
import com.vantruong.order.enums.EPaymentMethod;
import com.vantruong.order.enums.OrderStatus;
import com.vantruong.order.enums.PaymentStatus;
import com.vantruong.order.producer.KafkaProducer;
import com.vantruong.order.service.order.DeliveryAddressService;
import com.vantruong.order.service.order.OrderService;
import com.vantruong.order.service.payment.PaymentMethodService;
import com.vantruong.order.service.payment.VNPayService;
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
public class PaymentOrderHandlerImpl implements PaymentOrderHandler {
  OrderService orderService;
  VNPayService vnPayService;
  KafkaProducer kafkaProducer;
  OrderConverter orderConverter;
  PaymentMethodService paymentMethodService;
  DeliveryAddressService deliveryAddressService;

  @Override
  @Transactional
  public OrderCreateResponse placeOrder(OrderRequest orderRequest) {
    PaymentMethod paymentMethod = paymentMethodService.findById(orderRequest.getPaymentMethodId());

    Order newOrder = orderService.createNewOrder(orderRequest);
    DeliveryAddress deliveryAddress = deliveryAddressService.getByOrder(newOrder);

    String urlPayment = null;
    if(paymentMethod.getSlug().equals(EPaymentMethod.COD)) {
      OrderEvent orderEvent = orderConverter.orderToKafka(newOrder, deliveryAddress);
      kafkaProducer.sendOrder(orderEvent);
    } else if (paymentMethod.getSlug().equals(EPaymentMethod.VN_PAY)) {
      urlPayment = getPaymentUrl(newOrder.getOrderId(), newOrder.getTotalPrice());
    }
    return OrderCreateResponse.builder()
            .paymentMethod(paymentMethod)
            .urlPayment(urlPayment)
            .build();
  }

  private String getPaymentUrl(Integer orderId, Double amount) {
    return vnPayService.generatePaymentUrl(orderId, amount);
  }

  @KafkaListener(topics = KafkaTopics.PAYMENT_TOPIC)
  public void handlePaymentCallback(Map<String, String> params) {
    Boolean paymentResponse = vnPayService.handleVNPayCallBack(params);

    String orderId = params.get("vnp_OrderInfo").split(":")[1].trim();
    Order order = orderService.findById(Integer.parseInt(orderId));
    DeliveryAddress deliveryAddress = deliveryAddressService.getByOrder(order);

    OrderEvent orderEvent = orderConverter.orderToKafka(order, deliveryAddress);
    if (paymentResponse) {
      orderService.updateOrderStatus(Integer.parseInt(orderId), OrderStatus.PENDING_CONFIRM);
      orderService.updatePaymentStatus(Integer.parseInt(orderId), PaymentStatus.PAID);
      orderEvent.setOrderStatus(OrderEventStatus.NEW);
    } else {
      compensateOrder(orderEvent);
      orderEvent.setOrderStatus(OrderEventStatus.ROLLBACK);
    }
    kafkaProducer.sendOrder(orderEvent);
  }

  @KafkaListener(topics = KafkaTopics.ORDER_TOPIC)
  public void compensateOrder(OrderEvent orderEvent) {
    orderService.deleteOrder(orderEvent.getOrderId());
  }

}
