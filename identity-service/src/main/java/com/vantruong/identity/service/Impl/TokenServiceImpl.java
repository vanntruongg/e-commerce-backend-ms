package com.vantruong.identity.service.Impl;

import com.vantruong.identity.entity.Token;
import com.vantruong.identity.exception.ErrorCode;
import com.vantruong.identity.exception.NotFoundException;
import com.vantruong.identity.repository.TokenRepository;
import com.vantruong.identity.service.TokenService;
import com.vantruong.identity.constant.MessageConstant;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TokenServiceImpl implements TokenService {
  private final TokenRepository tokenRepository;

  @Override
  public Token findByTokenValue(String tokenValue) {
    return tokenRepository.findTokenByTokenValue(tokenValue).orElseThrow(()
            -> new NotFoundException(ErrorCode.NOT_FOUND, MessageConstant.INVALID_TOKEN));
  }

  @Override
  @Transactional
  public void createToken(Token token) {
    tokenRepository.save(token);
  }
}
