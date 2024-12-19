package com.vantruong.identity.controller;

import com.vantruong.identity.common.CommonResponse;
import com.vantruong.identity.dto.request.RoleRequest;
import com.vantruong.identity.constant.MessageConstant;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.vantruong.identity.constant.CommonApiEndpoint.CREATE;
import static com.vantruong.identity.constant.CommonApiEndpoint.IDENTITY;
import static com.vantruong.identity.constant.RoleApiEndpoint.ROLE;

@RestController
@RequestMapping(IDENTITY + ROLE)
public class RoleController {
  private final RoleService roleService;

  public RoleController(RoleService roleService) {
    this.roleService = roleService;
  }

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
