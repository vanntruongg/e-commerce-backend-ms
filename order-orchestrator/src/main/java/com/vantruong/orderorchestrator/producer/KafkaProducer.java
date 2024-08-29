package com.vantruong.orderorchestrator.producer;

import com.vantruong.common.constant.KafkaTopics;
import com.vantruong.common.event.OrderEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RequiredArgsConstructor
@Slf4j
public class KafkaProducer {
  private final KafkaTemplate<String, Object> kafkaTemplate;


  public void sendPaymentCallback(Map<String, String> params) {
    log.debug("Send message to topic: {}", KafkaTopics.PAYMENT_TOPIC);
    kafkaTemplate.send(KafkaTopics.PAYMENT_TOPIC, params);
  }

  public void send(String topic, OrderEvent orderEvent) {
    log.debug("Send message to topic: {}", topic);
    kafkaTemplate.send(topic, orderEvent);
  }
}
