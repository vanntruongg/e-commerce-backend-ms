package com.vantruong.gateway.config;

import com.vantruong.gateway.repository.IdentityClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@Configuration
public class WebClientConfig {

  @Bean
  WebClient webClient() {
    return WebClient.builder()
            .baseUrl("http://localhost:9001/internal/identity")
            .build();
  }

  @Bean
  IdentityClient identityClient(WebClient webClient) {
    HttpServiceProxyFactory httpServiceProxyFactory= HttpServiceProxyFactory
            .builderFor(WebClientAdapter.create(webClient)).build();

    return httpServiceProxyFactory.createClient(IdentityClient.class);
  }
}
