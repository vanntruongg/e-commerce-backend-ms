package com.vantruong.order.producer;

import com.vantruong.order.constant.KafkaTopic;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class KafkaProducer {
  private final KafkaTemplate<String, Object> kafkaTemplate;

  public void sendCreatedOrder() {
    log.debug("Sending to order topic={}", KafkaTopic.ORDER_CREATED);
//    kafkaTemplate.send(KafkaTopic.ORDER_CREATED);
  }

  public void sendPaymentSuccess() {
    log.debug("Sending to order topic={}", KafkaTopic.PAYMENT_SUCCESS);
//    kafkaTemplate.send( KafkaTopic.PAYMENT_SUCCESS);
  }

  public void sendCancelOrder() {
//    kafkaTemplate.send(ORDER_CANCEL_TOPIC, cancelOrderEvent);
  }

}
