package com.vantruong.rating.service;

import com.vantruong.common.dto.user.UserCommonDto;
import com.vantruong.rating.client.UserClient;
import com.vantruong.rating.common.CommonResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
  private final UserClient userClient;

  public UserCommonDto getUser() {
    CommonResponse<UserCommonDto> response = userClient.getUserProfile();
    return response.getData();
  }
}
