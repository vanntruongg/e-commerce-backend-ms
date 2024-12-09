package com.vantruong.orderorchestrator.controller;

import com.vantruong.orderorchestrator.service.OrderSagaOrchestrator;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

import static com.vantruong.orderorchestrator.constant.ApiEndpoint.ORDER_ORCHESTRATOR;
import static com.vantruong.orderorchestrator.constant.ApiEndpoint.VN_PAY_PAYMENT_CALLBACK;

@RestController
@RequestMapping(ORDER_ORCHESTRATOR)
@RequiredArgsConstructor
public class OrderOrchestratorController {
  private final OrderSagaOrchestrator orderSagaOrchestrator;

//  private static final String URL_ORDER_SUCCESS = "http://localhost:3000/thank-you";
//  private static final String URL_ORDER_FAILED = "http://localhost:3000/checkout";
  private static final String URL_ORDER_SUCCESS = "https://neststore-vtt.vercel.app/thank-you";
  private static final String URL_ORDER_FAILED = "https://neststore-vtt.vercel.app/checkout";

  @GetMapping(VN_PAY_PAYMENT_CALLBACK)
  public CompletableFuture<RedirectView> createOrder(@RequestParam Map<String, String> params) {
    return orderSagaOrchestrator.startSaga(params)
            .thenApply(result -> new RedirectView(URL_ORDER_SUCCESS))
            .exceptionally(ex -> new RedirectView(URL_ORDER_FAILED));
//            .thenApply(result -> ResponseEntity.status(HttpStatus.FOUND).header(HttpHeaders.LOCATION, URL_ORDER_SUCCESS))
//            .exceptionally(ex -> ResponseEntity.status(HttpStatus.FOUND).header(HttpHeaders.LOCATION, URL_ORDER_FAILED));
  }

}
