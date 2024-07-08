package identityservice.controller;

import identityservice.common.CommonResponse;
import identityservice.constant.MessageConstant;
import identityservice.dto.request.PermissionRequest;
import identityservice.service.PermissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static identityservice.constant.CommonApiEndpoint.*;
import static identityservice.constant.PermissionApiEndpoint.DELETE_PERMISSION;
import static identityservice.constant.PermissionApiEndpoint.PERMISSIONS;

@RestController
@RequiredArgsConstructor
@RequestMapping(IDENTITY + PERMISSIONS)
public class PermissionController {
  private final PermissionService permissionService;

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
