package com.vantruong.identity.service;

import com.vantruong.identity.constant.MessageConstant;
import com.vantruong.identity.entity.Role;
import com.vantruong.identity.enums.ERole;
import com.vantruong.identity.exception.ErrorCode;
import com.vantruong.identity.exception.NotFoundException;
import com.vantruong.identity.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoleService {
  private final RoleRepository roleRepository;

  public Role findByRole(ERole role) {
    return roleRepository.findById(role.getRole())
            .orElseThrow(() -> new NotFoundException(ErrorCode.NOT_FOUND, MessageConstant.ROLE_NOT_FOUND));
  }
}
