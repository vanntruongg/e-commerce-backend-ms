package com.vantruong.user.controller;

import com.vantruong.user.common.CommonResponse;
import com.vantruong.user.constant.MessageConstant;
import com.vantruong.user.service.OrderAddressService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.vantruong.user.constant.ApiEndpoint.*;

@RestController
@RequestMapping(INTERNAL + ADDRESS)
public class InternalAddressController {
  private final OrderAddressService orderDeliveryAddressService;

  public InternalAddressController(OrderAddressService orderDeliveryAddressService) {
    this.orderDeliveryAddressService = orderDeliveryAddressService;
  }

  @GetMapping(ORDER + GET + ID_PARAM)
  public ResponseEntity<CommonResponse<Object>> getOrderDeliveryAddressByAddressId(@PathVariable("id") Integer addressId) {
    return ResponseEntity.ok().body(CommonResponse.builder()
            .isSuccess(true)
            .message(MessageConstant.FIND_SUCCESS)
            .data(orderDeliveryAddressService.getOrderAddressById(addressId))
            .build());
  }
}

