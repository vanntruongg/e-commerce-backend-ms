package com.vantruong.user.service;

import com.vantruong.user.constant.MessageConstant;
import com.vantruong.user.exception.ErrorCode;
import com.vantruong.user.exception.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TokenService {
  private final TokenRepository tokenRepository;

  public TokenService(TokenRepository tokenRepository) {
    this.tokenRepository = tokenRepository;
  }

  public Token findByTokenValue(String tokenValue) {
    return tokenRepository.findTokenByTokenValue(tokenValue).orElseThrow(()
            -> new NotFoundException(ErrorCode.NOT_FOUND, MessageConstant.INVALID_TOKEN));
  }

  @Transactional
  public void createToken(Token token) {
    tokenRepository.save(token);
  }
}
