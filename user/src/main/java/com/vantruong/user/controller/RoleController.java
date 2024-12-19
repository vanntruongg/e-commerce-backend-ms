package com.vantruong.user.controller;

import com.vantruong.user.common.CommonResponse;
import com.vantruong.user.constant.MessageConstant;
import com.vantruong.user.dto.request.RoleRequest;
import com.vantruong.user.service.RoleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.vantruong.user.constant.AuthApiEndpoint.IDENTITY;
import static com.vantruong.user.constant.MessageConstant.CREATE;
import static com.vantruong.user.constant.RoleApiEndpoint.ROLE;


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
