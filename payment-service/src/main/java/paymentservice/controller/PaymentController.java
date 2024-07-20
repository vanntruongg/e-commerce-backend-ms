package paymentservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import paymentservice.common.CommonResponse;
import paymentservice.constant.MessageConstant;
import paymentservice.service.PaymentService;
import paymentservice.constant.ApiEndpoint;

import java.io.UnsupportedEncodingException;

@RestController
@RequestMapping(ApiEndpoint.PAYMENT)
@RequiredArgsConstructor
public class PaymentController {
  private final PaymentService paymentService;

  @GetMapping(ApiEndpoint.GET_URL_PAYMENT)
  public ResponseEntity<CommonResponse<Object>> getOrderPayment(@RequestParam("amount") long amount) throws UnsupportedEncodingException {
    return ResponseEntity.ok().body(CommonResponse.builder()
            .isSuccess(true)
            .message(MessageConstant.GET_LINK_PAYMENT_SUCCESS)
            .data(paymentService.createUrlPaymentOrder(amount))
            .build());
  }
}
