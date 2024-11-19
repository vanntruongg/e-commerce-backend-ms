package com.vantruong.inventory.service;

import com.vantruong.common.constant.KafkaTopics;
import com.vantruong.common.dto.order.OrderItemCommonDto;
import com.vantruong.common.dto.request.ProductQuantityRequest;
import com.vantruong.common.event.OrderEvent;
import com.vantruong.common.event.OrderEventStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class OrderManagementService {

}
