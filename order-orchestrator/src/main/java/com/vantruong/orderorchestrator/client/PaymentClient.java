//package com.vantruong.orderorchestrator.client;
//
//import com.vantruong.common.constant.InternalApiEndpoint;
//import com.vantruong.common.dto.request.PaymentUrlRequest;
//import com.vantruong.common.dto.response.PaymentUrlResponse;
//import com.vantruong.orderorchestrator.common.CommonResponse;
//import org.springframework.cloud.openfeign.FeignClient;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//
//@FeignClient(name = "payment-service", url = InternalApiEndpoint.PAYMENT_SERVICE_URL)
//public interface PaymentClient {
//  @PostMapping(InternalApiEndpoint.GET_PAYMENT_URL)
//  CommonResponse<PaymentUrlResponse> getPaymentUrl(@RequestBody PaymentUrlRequest paymentRequest);
//}