package identityservice.service;

import identityservice.entity.Token;

public interface TokenService {
  Token findByTokenValue(String tokenValue);
  void createToken(Token token);
}
