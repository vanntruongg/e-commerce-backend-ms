package com.vantruong.order.controller;

import lombok.RequiredArgsConstructor;
import com.vantruong.order.service.OrderDetailService;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class OrderDetailController {
  private final OrderDetailService orderDetailService;
}
