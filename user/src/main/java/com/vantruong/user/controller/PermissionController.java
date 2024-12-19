package com.vantruong.user.controller;

import com.vantruong.user.common.CommonResponse;
import com.vantruong.user.constant.MessageConstant;
import com.vantruong.user.dto.request.PermissionRequest;
import com.vantruong.user.service.PermissionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.vantruong.user.constant.ApiEndpoint.CREATE;
import static com.vantruong.user.constant.AuthApiEndpoint.IDENTITY;
import static com.vantruong.user.constant.PermissionApiEndpoint.DELETE_PERMISSION;
import static com.vantruong.user.constant.PermissionApiEndpoint.PERMISSIONS;


@RestController
@RequestMapping(IDENTITY + PERMISSIONS)
public class PermissionController {
  private final PermissionService permissionService;

  public PermissionController(PermissionService permissionService) {
    this.permissionService = permissionService;
  }

  @PostMapping(CREATE)
  public ResponseEntity<CommonResponse<Object>> create(@RequestBody PermissionRequest request) {
    return ResponseEntity.ok().body(CommonResponse.builder()
            .isSuccess(true)
            .message(MessageConstant.CREATE_SUCCESS)
            .data(permissionService.create(request))
            .build());
  }

  @GetMapping
  public ResponseEntity<CommonResponse<Object>> getAll() {
    return ResponseEntity.ok().body(CommonResponse.builder()
            .isSuccess(true)
            .message(MessageConstant.SUCCESS)
            .data(permissionService.getAll())
            .build());
  }

  @DeleteMapping(DELETE_PERMISSION)
  public ResponseEntity<CommonResponse<Object>> delete(@PathVariable("permission") String permission) {
    permissionService.delete(permission);
    return ResponseEntity.ok().body(CommonResponse.builder()
            .isSuccess(true)
            .message(MessageConstant.DELETE_SUCCESS)
            .build());
  }

}
