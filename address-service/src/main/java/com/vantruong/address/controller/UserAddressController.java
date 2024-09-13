package com.vantruong.address.controller;

import com.vantruong.address.common.CommonResponse;
import com.vantruong.address.constant.MessageConstant;
import com.vantruong.address.dto.UserAddressRequest;
import com.vantruong.address.service.UserAddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.vantruong.address.constant.ApiEndpoint.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(ADDRESS + USER)
public class UserAddressController {
  private final UserAddressService userAddressService;

  @PostMapping(CREATE)
  public ResponseEntity<CommonResponse<Object>> createUserAddress(@RequestBody UserAddressRequest request) {
    return ResponseEntity.ok().body(CommonResponse.builder()
            .isSuccess(true)
            .message(MessageConstant.CREATE_ADDRESS_SUCCESS)
            .data(userAddressService.createUserAddress(request))
            .build());
  }

  @GetMapping()
  public ResponseEntity<CommonResponse<Object>> getAllAddressByUserId() {
    return ResponseEntity.ok().body(CommonResponse.builder()
            .isSuccess(true)
            .message(MessageConstant.FIND_SUCCESS)
            .data(userAddressService.getAllAddressByUserId())
            .build());
  }

  @GetMapping(DEFAULT)
  public ResponseEntity<CommonResponse<Object>> getAddressDefault() {
    return ResponseEntity.ok().body(CommonResponse.builder()
            .isSuccess(true)
            .message(MessageConstant.FIND_SUCCESS)
            .data(userAddressService.getAddressDefault())
            .build());
  }

  @PostMapping(UPDATE)
  public ResponseEntity<CommonResponse<Object>> updateUserAddress(@RequestBody UserAddressRequest request) {
    if (userAddressService.updateUserAddress(request)) {
      return ResponseEntity.ok().body(CommonResponse.builder()
              .isSuccess(true)
              .message(MessageConstant.UPDATE_ADDRESS_SUCCESS)
              .build());
    }
    return ResponseEntity.badRequest().body(CommonResponse.builder()
            .isSuccess(false)
            .message(MessageConstant.UPDATE_ADDRESS_FAIL)
            .build());
  }

  @PostMapping(UPDATE + DEFAULT)
  public ResponseEntity<CommonResponse<Object>> setDefaultAddress(
          @RequestParam("addressId") Integer addressId
  ) {
    return ResponseEntity.ok().body(CommonResponse.builder()
            .isSuccess(true)
            .message(MessageConstant.UPDATE_ADDRESS_SUCCESS)
            .data(userAddressService.setDefaultAddress(addressId))
            .build());
  }

  @DeleteMapping(DELETE)
  public ResponseEntity<CommonResponse<Object>> deleteAddress(
          @RequestParam("addressId") Integer addressId
  ) {
    return ResponseEntity.ok().body(CommonResponse.builder()
            .isSuccess(true)
            .message(MessageConstant.DELETE_SUCCESS)
            .data(userAddressService.deleteAddress(addressId))
            .build());
  }

}
