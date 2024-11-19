package com.vantruong.orderorchestrator.service;

import com.vantruong.common.constant.KafkaTopics;
import com.vantruong.common.event.OrderEvent;
import com.vantruong.common.event.OrderEventStatus;
import com.vantruong.orderorchestrator.producer.KafkaProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;

@Service
@RequiredArgsConstructor
public class OrderSagaOrchestrator {
  private final KafkaProducer kafkaProducer;

  private final ConcurrentHashMap<String, CompletableFuture<String>> sagaResults = new ConcurrentHashMap<>();

  public CompletableFuture<String> startSaga(Map<String, String> params) {
    CompletableFuture<String> resultFuture = new CompletableFuture<>();
    String orderId = params.get("vnp_OrderInfo").split(":")[1].trim();
    sagaResults.put(orderId, resultFuture);

    // Bắt đầu với việc gửi sự kiện đến Order Service
    kafkaProducer.sendPaymentCallback(params);
    return resultFuture;
  }


//  @KafkaListener(topics = KafkaTopics.ORDER_CREATED_TOPIC)
//  public void onOrderUpdated(OrderEvent orderEvent) {
//    if (orderEvent.getOrderEventStatus() == OrderEventStatus.NEW) {
//      kafkaProducer.send(KafkaTopics.INVENTORY_TOPIC, orderEvent);
//    } else if (orderEvent.getOrderEventStatus() == OrderEventStatus.ROLLBACK) {
//      CompletableFuture<String> resultFuture = sagaResults.remove(orderEvent.getOrderId().toString());
//      if (resultFuture != null) {
//        compensateOrder(orderEvent);
//        resultFuture.completeExceptionally(new RuntimeException("Order processing failed"));
//      }
//    }
//  }

  @KafkaListener(topics = {KafkaTopics.INVENTORY_RESPONSE, KafkaTopics.PAYMENT_SUCCESS })
  public void onInventoryUpdated(OrderEvent orderEvent) {
    CompletableFuture<String> resultFuture = sagaResults.get(orderEvent.getOrderId().toString());
    if (orderEvent.getOrderEventStatus() == OrderEventStatus.NEW) {
      completeOrder(orderEvent);
      if (resultFuture != null) {
        resultFuture.complete("Order completed successfully");
        sagaResults.remove(orderEvent.getOrderId().toString());
      }
    } else if (orderEvent.getOrderEventStatus() == OrderEventStatus.ROLLBACK) {
      compensateOrder(orderEvent);
      if (resultFuture != null) {
        resultFuture.completeExceptionally(new RuntimeException("Order processing failed and rolled back."));
        sagaResults.remove(orderEvent.getOrderId().toString());
      }
    }
  }

  private void completeOrder(OrderEvent orderEvent) {
    kafkaProducer.send(KafkaTopics.CART_TOPIC, orderEvent);
    kafkaProducer.send(KafkaTopics.NOTIFICATION_TOPIC, orderEvent);
  }


  private void compensateOrder(OrderEvent orderEvent) {
    kafkaProducer.send(KafkaTopics.ORDER_TOPIC, orderEvent);
  }

}
