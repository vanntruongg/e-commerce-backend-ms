package com.vantruong.address.controller;

import com.vantruong.address.common.CommonResponse;
import com.vantruong.address.constant.MessageConstant;
import com.vantruong.address.service.AddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.vantruong.address.constant.ApiEndpoint.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(ADDRESS)
public class AddressController {
  private final AddressService addressService;

  @GetMapping(GET + DATA)
  public ResponseEntity<CommonResponse<Object>> getAddress(@RequestParam(value = "parentCode", defaultValue = "null") String parentCode) {
    return ResponseEntity.ok().body(CommonResponse.builder()
                    .isSuccess(true)
                    .message(MessageConstant.FIND_SUCCESS)
                    .data(addressService.getAddressDataByParentCode(parentCode))
            .build());
  }
  @GetMapping("/validate")
  public ResponseEntity<CommonResponse<Object>> getAddress(
          @RequestParam("provinceId") int provinceId,
          @RequestParam("districtId") int districtId,
          @RequestParam("wardId") int wardId
  ) {
    return ResponseEntity.ok().body(CommonResponse.builder()
            .isSuccess(true)
            .message(MessageConstant.FIND_SUCCESS)
            .data(addressService.addressIsValid(provinceId, districtId, wardId))
            .build());
  }
}
