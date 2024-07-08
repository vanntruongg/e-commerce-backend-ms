package gateway.service;

import gateway.common.CommonResponse;
import gateway.dto.request.IntrospectRequest;
import gateway.dto.response.IntrospectResponse;
import gateway.repository.IdentityClient;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class IdentityService {
  IdentityClient identityClient;

  public Mono<CommonResponse<IntrospectResponse>> introspect(String token) {
    return identityClient.introspect(IntrospectRequest.builder()
            .token(token)
            .build());
  }
}
