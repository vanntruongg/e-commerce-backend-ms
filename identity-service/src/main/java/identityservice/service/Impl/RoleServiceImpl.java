package identityservice.service.Impl;

import identityservice.constant.MessageConstant;
import identityservice.dto.request.RoleRequest;
import identityservice.dto.response.PermissionResponse;
import identityservice.dto.response.RoleResponse;
import identityservice.entity.Role;
import identityservice.exception.ErrorCode;
import identityservice.exception.NotFoundException;
import identityservice.repository.RoleRepository;
import identityservice.service.PermissionService;
import identityservice.service.RoleService;
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
