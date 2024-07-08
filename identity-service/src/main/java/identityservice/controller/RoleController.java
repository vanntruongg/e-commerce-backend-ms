package identityservice.controller;

import identityservice.common.CommonResponse;
import identityservice.constant.MessageConstant;
import identityservice.dto.request.RoleRequest;
import identityservice.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static identityservice.constant.CommonApiEndpoint.CREATE;
import static identityservice.constant.CommonApiEndpoint.IDENTITY;
import static identityservice.constant.RoleApiEndpoint.ROLE;

@RestController
@RequiredArgsConstructor
@RequestMapping(IDENTITY + ROLE)
public class RoleController {
  private final RoleService roleService;

  @GetMapping
  public ResponseEntity<CommonResponse<Object>> getAll() {
    return ResponseEntity.ok().body(CommonResponse.builder()
            .isSuccess(true)
            .message(MessageConstant.FIND_SUCCESS)
            .data(roleService.findAll())
            .build());
  }

  @PostMapping(CREATE)
  public ResponseEntity<CommonResponse<Object>> create(@RequestBody RoleRequest request) {
    return ResponseEntity.ok().body(CommonResponse.builder()
            .isSuccess(true)
            .message(MessageConstant.FIND_SUCCESS)
            .data(roleService.create(request))
            .build());
  }
}
