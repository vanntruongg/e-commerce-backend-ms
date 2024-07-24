package com.vantruong.identity.service.Impl;

import com.vantruong.identity.dto.request.PermissionRequest;
import com.vantruong.identity.dto.response.PermissionResponse;
import com.vantruong.identity.entity.Permission;
import com.vantruong.identity.repository.PermissionRepository;
import com.vantruong.identity.service.PermissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class PermissionServiceImpl implements PermissionService {
  private final PermissionRepository permissionRepository;

  @Override
  public PermissionResponse create(PermissionRequest request) {
    Permission permission = Permission.builder()
            .name(request.getName())
            .description(request.getDescription())
            .build();
    permissionRepository.save(permission);
    return PermissionResponse.builder()
            .name(permission.getName())
            .description(permission.getDescription())
            .build();
  }

  @Override
  public List<PermissionResponse> getAll() {
    return mapToPermissionResponse(permissionRepository.findAll());
  }

  @Override
  public void delete(String permission) {
    permissionRepository.deleteById(permission);
  }

  @Override
  public List<Permission> findAllById(Set<String> permissionsName) {
    return permissionRepository.findAllById(permissionsName);
  }

  public List<PermissionResponse> mapToPermissionResponse(List<Permission> permissions) {
    return permissions.stream()
            .map(permission -> PermissionResponse.builder()
                    .name(permission.getName())
                    .description(permission.getDescription())
                    .build())
            .toList();
  }
}
