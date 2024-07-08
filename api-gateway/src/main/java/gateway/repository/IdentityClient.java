package gateway.repository;


import gateway.common.CommonResponse;
import gateway.dto.request.IntrospectRequest;
import gateway.dto.response.IntrospectResponse;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.PostExchange;
import reactor.core.publisher.Mono;

public interface IdentityClient {
  @PostExchange(url = "/introspect", contentType = MediaType.APPLICATION_JSON_VALUE)
  Mono<CommonResponse<IntrospectResponse>> introspect(@RequestBody IntrospectRequest request);
}
