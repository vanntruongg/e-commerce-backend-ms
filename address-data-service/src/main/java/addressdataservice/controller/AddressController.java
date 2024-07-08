package addressdataservice.controller;

import addressdataservice.common.CommonResponse;
import addressdataservice.constant.MessageConstant;
import addressdataservice.service.AddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static addressdataservice.constant.ApiEndpoint.*;

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
