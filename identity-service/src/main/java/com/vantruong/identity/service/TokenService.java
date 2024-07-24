package com.vantruong.identity.service;

import com.vantruong.identity.entity.Token;

public interface TokenService {
  Token findByTokenValue(String tokenValue);
  void createToken(Token token);
}
