package addressdataservice.controller;

import addressdataservice.common.CommonResponse;
import addressdataservice.constant.MessageConstant;
import addressdataservice.service.OrderDeliveryAddressService;
import addressdataservice.service.UserAddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static addressdataservice.constant.ApiEndpoint.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(  ADDRESS + ORDER)
public class OrderDeliveryAddressController {
  private final OrderDeliveryAddressService orderDeliveryAddressService;

  @GetMapping(GET + ID_PARAM)
  public ResponseEntity<CommonResponse<Object>> getUserAddressById(@PathVariable("id") Integer addressId) {
    return ResponseEntity.ok().body(CommonResponse.builder()
            .isSuccess(true)
            .message(MessageConstant.FIND_SUCCESS)
            .data(orderDeliveryAddressService.getDeliveryAddressById(addressId))
            .build());
  }
}

