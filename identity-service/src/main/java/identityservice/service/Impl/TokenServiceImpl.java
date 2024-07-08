package identityservice.service.Impl;

import identityservice.constant.MessageConstant;
import identityservice.entity.Token;
import identityservice.exception.ErrorCode;
import identityservice.exception.NotFoundException;
import identityservice.repository.TokenRepository;
import identityservice.service.TokenService;
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
