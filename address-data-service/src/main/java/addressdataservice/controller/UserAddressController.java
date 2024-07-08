package addressdataservice.controller;

import addressdataservice.common.CommonResponse;
import addressdataservice.constant.MessageConstant;
import addressdataservice.dto.UserAddressRequest;
import addressdataservice.service.UserAddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static addressdataservice.constant.ApiEndpoint.*;

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

  @GetMapping(EMAIL_PARAM)
  public ResponseEntity<CommonResponse<Object>> getAllAddressByUserId(@PathVariable(VARIABLE_EMAIL) String email) {
    return ResponseEntity.ok().body(CommonResponse.builder()
            .isSuccess(true)
            .message(MessageConstant.FIND_SUCCESS)
            .data(userAddressService.getAllAddressByUserId(email))
            .build());
  }

  @GetMapping(DEFAULT + EMAIL_PARAM)
  public ResponseEntity<CommonResponse<Object>> getAddressDefault(@PathVariable(VARIABLE_EMAIL) String email) {
    return ResponseEntity.ok().body(CommonResponse.builder()
            .isSuccess(true)
            .message(MessageConstant.FIND_SUCCESS)
            .data(userAddressService.getAddressDefault(email))
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

  @PostMapping( UPDATE + DEFAULT)
  public ResponseEntity<CommonResponse<Object>> setDefaultAddress(
          @RequestParam("email") String email,
          @RequestParam("addressId") Integer addressId
  ) {
    return ResponseEntity.ok().body(CommonResponse.builder()
            .isSuccess(true)
            .message(MessageConstant.UPDATE_ADDRESS_SUCCESS)
            .data(userAddressService.setDefaultAddress(email, addressId))
            .build());
  }

  @DeleteMapping( DELETE + ID_PARAM)
  public ResponseEntity<CommonResponse<Object>> deleteAddress(
          @RequestParam("email") String email,
          @RequestParam("addressId") Integer addressId
  ) {
    return ResponseEntity.ok().body(CommonResponse.builder()
            .isSuccess(true)
            .message(MessageConstant.DELETE_SUCCESS)
            .data(userAddressService.deleteAddress(email, addressId))
            .build());
  }

}
