package com.vantruong.cart;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;

@SpringBootApplication
@EnableRedisRepositories
@EnableFeignClients
public class CartServiceApplication {
  public static void main(String[] args) {
    SpringApplication.run(CartServiceApplication.class, args);

  }

}
