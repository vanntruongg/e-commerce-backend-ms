package com.vantruong.user.service;

import com.vantruong.user.constant.MessageConstant;
import com.vantruong.user.dto.request.RoleRequest;
import com.vantruong.user.dto.response.PermissionResponse;
import com.vantruong.user.dto.response.RoleResponse;
import com.vantruong.user.exception.ErrorCode;
import com.vantruong.user.exception.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;

@Service
public class RoleService {
  private final RoleRepository roleRepository;
  private final PermissionService permissionService;

  public RoleService(RoleRepository roleRepository, PermissionService permissionService) {
    this.roleRepository = roleRepository;
    this.permissionService = permissionService;
  }

  public RoleResponse create(RoleRequest request) {
    var permissions = permissionService.findAllById(request.getPermissions());
    Role role = Role.builder()
            .name(request.getName())
            .description(request.getDescription())
            .permissions(new HashSet<>(permissions))
            .build();
    roleRepository.save(role);

    List<PermissionResponse> permissionResponses = permissionService.mapToPermissionResponse(permissions);

    return RoleResponse.builder()
            .name(role.getName())
            .description(role.getDescription())
            .permissions(permissionResponses)
            .build();
  }

  public Role findById(String name) {
    return roleRepository.findById(name).orElseThrow(() -> new NotFoundException(ErrorCode.NOT_FOUND, MessageConstant.NOT_FOUND));
  }

  public List<RoleResponse> findAll() {
    return mapToRoleResponse(roleRepository.findAll());
  }

  public List<Role> findAllById(List<String> roleNames) {
    return roleRepository.findAllById(roleNames);
  }

  private List<RoleResponse> mapToRoleResponse(List<Role> roles) {
    return roles.stream().map(role -> RoleResponse.builder()
            .name(role.getName())
            .description(role.getDescription())
            .build()).toList();
  }

}
