package com.vantruong.order.repository.client;

import com.vantruong.order.common.CommonResponse;
import com.vantruong.order.constant.InternalApiEndpoint;
import com.vantruong.order.dto.PaymentRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "payment-service", url = InternalApiEndpoint.PAYMENT_SERVICE_URL)
public interface PaymentClient {
  @DeleteMapping(InternalApiEndpoint.PAY)
  CommonResponse<Object> createPayment(@RequestBody PaymentRequest paymentRequest);
}