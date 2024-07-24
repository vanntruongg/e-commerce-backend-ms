package com.vantruong.identity.repository;

import com.vantruong.identity.entity.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TokenRepository extends JpaRepository<Token, Integer> {
  Optional<Token> findTokenByTokenValue(String tokenValue);
}
