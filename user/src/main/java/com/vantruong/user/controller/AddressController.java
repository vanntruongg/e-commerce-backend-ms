package com.vantruong.user.controller;

import com.vantruong.user.common.CommonResponse;
import com.vantruong.user.constant.MessageConstant;
import com.vantruong.user.service.AddressService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.vantruong.user.constant.ApiEndpoint.*;

@RestController
@RequestMapping(ADDRESS)
public class AddressController {
  private final AddressService addressService;

  public AddressController(AddressService addressService) {
    this.addressService = addressService;
  }

  @GetMapping(GET_DATA)
  public ResponseEntity<CommonResponse<Object>> getAddress(
          @RequestParam(value = "parentCode", defaultValue = "null") String parentCode
  ) {
    return ResponseEntity.ok().body(CommonResponse.builder()
            .isSuccess(true)
            .message(MessageConstant.FIND_SUCCESS)
            .data(addressService.getAddressDataByParentCode(parentCode))
            .build());
  }

  @GetMapping(VALIDATE)
  public ResponseEntity<CommonResponse<Object>> getAddress(
          @RequestParam(PROVINCE_ID) int provinceId,
          @RequestParam(DISTRICT_ID) int districtId,
          @RequestParam(WARD_ID) int wardId
  ) {
    return ResponseEntity.ok().body(CommonResponse.builder()
            .isSuccess(true)
            .message(MessageConstant.FIND_SUCCESS)
            .data(addressService.addressIsValid(provinceId, districtId, wardId))
            .build());
  }
}
