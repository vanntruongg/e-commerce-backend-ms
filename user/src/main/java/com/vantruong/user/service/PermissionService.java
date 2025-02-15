package com.vantruong.user.service;

import com.vantruong.user.dto.request.PermissionRequest;
import com.vantruong.user.dto.response.PermissionResponse;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class PermissionService {
  private final PermissionRepository permissionRepository;

  public PermissionService(PermissionRepository permissionRepository) {
    this.permissionRepository = permissionRepository;
  }

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

  public List<PermissionResponse> getAll() {
    return mapToPermissionResponse(permissionRepository.findAll());
  }

  public void delete(String permission) {
    permissionRepository.deleteById(permission);
  }

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
