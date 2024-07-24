package com.vantruong.identity.service;

import com.vantruong.identity.dto.response.RoleResponse;
import com.vantruong.identity.entity.Role;
import com.vantruong.identity.dto.request.RoleRequest;

import java.util.List;

public interface RoleService {
  Role findById(String name);
  List<RoleResponse> findAll();
  List<Role> findAllById(List<String> roleNames);

  RoleResponse create(RoleRequest request);
}
