package paymentservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import paymentservice.common.CommonResponse;
import paymentservice.constant.ApiEndpoint;
import paymentservice.constant.MessageConstant;
import paymentservice.dto.PaymentRequest;
import paymentservice.gateway.PaymentGateway;

@RestController
@RequestMapping(ApiEndpoint.PAYMENTS)
@RequiredArgsConstructor
public class PaymentController {
  private final PaymentGateway paymentGateway;

  @GetMapping(ApiEndpoint.PAY)
  public ResponseEntity<CommonResponse<Object>> getOrderPayment(@RequestBody PaymentRequest paymentRequest) {

    return ResponseEntity.ok().body(CommonResponse.builder()
            .isSuccess(true)
            .message(MessageConstant.PAYMENT_SUCCESS)
            .data(paymentGateway.makePayment(paymentRequest))
            .build());

  }
}
