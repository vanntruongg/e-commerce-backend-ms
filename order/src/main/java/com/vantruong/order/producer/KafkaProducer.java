package com.vantruong.order.producer;

import com.vantruong.common.constant.KafkaTopics;
import com.vantruong.common.event.CancelOrderEvent;
import com.vantruong.common.event.OrderEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class KafkaProducer {
  private final KafkaTemplate<String, Object> kafkaTemplate;

  public void sendCreatedOrder(OrderEvent orderEvent) {
    log.debug("Sending to order topic={}", KafkaTopics.ORDER_CREATED_TOPIC);
    kafkaTemplate.send(KafkaTopics.ORDER_CREATED_TOPIC, orderEvent);
  }

  public void sendPaymentSuccess(OrderEvent orderEvent) {
    log.debug("Sending to order topic={}", KafkaTopics.PAYMENT_SUCCESS);
    kafkaTemplate.send(KafkaTopics.PAYMENT_SUCCESS, orderEvent);
  }

  public void sendCancelOrder(CancelOrderEvent cancelOrderEvent) {
    kafkaTemplate.send(KafkaTopics.ORDER_CANCEL_TOPIC, cancelOrderEvent);
  }

}
