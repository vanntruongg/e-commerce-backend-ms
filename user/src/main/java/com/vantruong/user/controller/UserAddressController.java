package com.vantruong.user.controller;

import com.vantruong.user.common.CommonResponse;
import com.vantruong.user.constant.MessageConstant;
import com.vantruong.user.dto.UserAddressRequest;
import com.vantruong.user.service.UserAddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.vantruong.user.constant.ApiEndpoint.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(USER + ADDRESS)
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
          @RequestParam(ADDRESS_ID) Integer addressId
  ) {
    return ResponseEntity.ok().body(CommonResponse.builder()
            .isSuccess(true)
            .message(MessageConstant.UPDATE_ADDRESS_SUCCESS)
            .data(userAddressService.setDefaultAddress(addressId))
            .build());
  }

  @DeleteMapping(DELETE)
  public ResponseEntity<CommonResponse<Object>> deleteAddress(
          @RequestParam(ADDRESS_ID) Integer addressId
  ) {
    return ResponseEntity.ok().body(CommonResponse.builder()
            .isSuccess(true)
            .message(MessageConstant.DELETE_SUCCESS)
            .data(userAddressService.deleteAddress(addressId))
            .build());
  }

}
