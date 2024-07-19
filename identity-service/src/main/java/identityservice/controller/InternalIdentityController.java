package identityservice.controller;

import com.nimbusds.jose.JOSEException;
import identityservice.common.CommonResponse;
import identityservice.dto.request.IntrospectRequest;
import identityservice.service.AuthService;
import identityservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;

import static identityservice.constant.AuthApiEndpoint.IDENTITY;
import static identityservice.constant.InternalApiEndpoint.*;


@RestController
@RequestMapping(INTERNAL + IDENTITY)
@RequiredArgsConstructor
public class InternalIdentityController {

  private final AuthService identityService;
  private final UserService userService;

  @PostMapping(INTROSPECT)
  public ResponseEntity<CommonResponse<Object>> authenticate(@RequestBody IntrospectRequest request) throws ParseException, JOSEException {
    return ResponseEntity.ok().body(CommonResponse.builder()
            .isSuccess(identityService.introspect(request).isValid())
            .data(identityService.introspect(request))
            .build());
  }

  @GetMapping(USER_EXISTED_BY_EMAIL)
  public ResponseEntity<CommonResponse<Object>> existedByEmail(@RequestParam("email") String email) {
    return ResponseEntity.ok().body(CommonResponse.builder()
            .data(userService.existedByEmail(email))
            .build());
  }
}
