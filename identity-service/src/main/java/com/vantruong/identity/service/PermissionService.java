package com.vantruong.identity.service;

import com.vantruong.identity.dto.response.PermissionResponse;
import com.vantruong.identity.entity.Permission;
import com.vantruong.identity.dto.request.PermissionRequest;

import java.util.List;
import java.util.Set;

public interface PermissionService {
  PermissionResponse create(PermissionRequest request);
  List<PermissionResponse> getAll();
  void delete(String permission);
  List<Permission> findAllById(Set<String> permissionsName);
  List<PermissionResponse> mapToPermissionResponse(List<Permission> permissions);
}
