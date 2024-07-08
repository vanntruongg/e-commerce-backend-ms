package identityservice.service;

import identityservice.dto.request.PermissionRequest;
import identityservice.dto.response.PermissionResponse;
import identityservice.entity.Permission;

import java.util.List;
import java.util.Set;

public interface PermissionService {
  PermissionResponse create(PermissionRequest request);
  List<PermissionResponse> getAll();
  void delete(String permission);
  List<Permission> findAllById(Set<String> permissionsName);
  List<PermissionResponse> mapToPermissionResponse(List<Permission> permissions);
}
