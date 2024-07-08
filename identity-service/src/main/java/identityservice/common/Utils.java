package identityservice.common;

import identityservice.entity.Token;
import identityservice.enums.TokenType;
import org.apache.commons.lang3.RandomStringUtils;

import java.time.LocalDateTime;

public class Utils {

  private static String generateRandomString() {
    int length = 24;
    boolean useLetters = true;
    boolean useNumbers = true;
    return RandomStringUtils.random(length, useLetters, useNumbers);
  }

  public static Token generateToken(TokenType type) {
    String randomToken = generateRandomString();
    LocalDateTime expiredDate = LocalDateTime.now().plusDays(1);
    return Token.builder()
            .tokenType(type.getTokenType())
            .tokenValue(randomToken)
            .expiredDate(expiredDate)
            .build();
  }
}
