package com.vantruong.identity.service.Impl;

import com.vantruong.common.exception.ErrorCode;
import com.vantruong.common.exception.NotFoundException;
import com.vantruong.identity.dto.response.PermissionResponse;
import com.vantruong.identity.dto.response.RoleResponse;
import com.vantruong.identity.constant.MessageConstant;
import com.vantruong.identity.dto.request.RoleRequest;
import com.vantruong.identity.entity.Role;
import com.vantruong.identity.repository.RoleRepository;
import com.vantruong.identity.service.PermissionService;
import com.vantruong.identity.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {
  private final RoleRepository roleRepository;
  private final PermissionService permissionService;

  @Override
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

  @Override
  public Role findById(String name) {
    return roleRepository.findById(name).orElseThrow(() -> new NotFoundException(ErrorCode.NOT_FOUND, MessageConstant.NOT_FOUND));
  }

  @Override
  public List<RoleResponse> findAll() {
    return mapToRoleResponse(roleRepository.findAll());
  }

  @Override
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
