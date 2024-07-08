package identityservice.service;

import identityservice.dto.request.RoleRequest;
import identityservice.dto.response.RoleResponse;
import identityservice.entity.Role;

import java.util.List;

public interface RoleService {
  Role findById(String name);
  List<RoleResponse> findAll();
  List<Role> findAllById(List<String> roleNames);

  RoleResponse create(RoleRequest request);
}
