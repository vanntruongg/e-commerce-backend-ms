package com.vantruong.identity.common;

import com.vantruong.identity.entity.Token;
import com.vantruong.identity.enums.TokenType;
import org.apache.commons.lang.RandomStringUtils;

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
