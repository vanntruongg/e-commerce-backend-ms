package identityservice.dto.request;

import lombok.Getter;

@Getter
public class VerifyEmailRequest {
  private String token;
}
