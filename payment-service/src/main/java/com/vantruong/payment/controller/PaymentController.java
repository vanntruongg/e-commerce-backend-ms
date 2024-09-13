//package com.vantruong.payment.controller;
//
//import com.vantruong.payment.common.CommonResponse;
//import com.vantruong.payment.constant.MessageConstant;
//import com.vantruong.payment.service.PaymentService;
//import com.vantruong.payment.service.VNPayService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//@RestController
//@RequestMapping(ApiEndpoint.PAYMENTS)
//@RequiredArgsConstructor
//public class PaymentController {
//  private final PaymentService paymentService;
//  private final VNPayService vnPayService;
//
//  @GetMapping(ApiEndpoint.GET_BY_ORDER_ID)
//  public ResponseEntity<CommonResponse<Object>> getPaymentByOrderId(@PathVariable("id") int orderId) {
//
//    return ResponseEntity.ok().body(CommonResponse.builder()
//            .isSuccess(true)
//            .message(MessageConstant.SUCCESS)
//            .data(paymentService.getPaymentByOrderId(orderId))
//            .build());
//  }
//
//
////  @GetMapping(ApiEndpoint.UPDATE_PAYMENT_STATUS)
////  public ResponseEntity<CommonResponse<Object>> updateOrderPaymentStatusToPaid(@PathVariable("id") int orderId) {
////
////    return ResponseEntity.ok().body(CommonResponse.builder()
////            .isSuccess(true)
////            .message(MessageConstant.SUCCESS)
////            .data(paymentService.updateOrderPaymentStatusToPaid(orderId))
////            .build());
////  }
//}
