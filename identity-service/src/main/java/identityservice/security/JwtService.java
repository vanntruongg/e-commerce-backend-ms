package identityservice.security;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import identityservice.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.text.ParseException;
import java.util.Date;
import java.util.StringJoiner;

@Service
@Slf4j
public class JwtService {

  @Value("${jwt.secret}")
  private String secret;
  @Value("${jwt.expire-time-access-token}")
  private long expireTimeAccessToken;
  @Value("${jwt.expire-time-refresh-token}")
  private long expireTimeRefreshToken;

  private String buildRole(User user) {
    StringJoiner stringJoiner = new StringJoiner(" ");
    if (!CollectionUtils.isEmpty(user.getRoles()))
      user.getRoles().forEach(role -> {
        stringJoiner.add(role.getName());
        if (!CollectionUtils.isEmpty(role.getPermissions()))
          role.getPermissions().forEach(permission -> stringJoiner.add(permission.getName()));
      });
    return stringJoiner.toString();
  }

  public String generateToken(UserDetailsImpl userDetails, boolean isAccessToken) {
    JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);

    JWTClaimsSet.Builder claimsSet = new JWTClaimsSet.Builder()
            .subject(userDetails.getUsername())
            .issuer("vantruong.com")
            .issueTime(new Date())
            .expirationTime(new Date(System.currentTimeMillis() + (isAccessToken ? expireTimeAccessToken : expireTimeRefreshToken)));

    if (isAccessToken) {
      claimsSet
              .claim("email", userDetails.getUsername())
              .claim("roles", buildRole(userDetails.getUser()));
    }

    Payload payload = new Payload(claimsSet.build().toJSONObject());

    JWSObject jwsObject = new JWSObject(header, payload);

    try {
      jwsObject.sign(new MACSigner(secret.getBytes()));
      return jwsObject.serialize();
    } catch (JOSEException e) {
      log.error("Can't create token: {}", e.getMessage());
      throw new RuntimeException(e);
    }
  }

  public String getEmailFromToken(String token) {
    try {
      SignedJWT signedJWT = SignedJWT.parse(token);
      return signedJWT.getJWTClaimsSet().getSubject();
    } catch (ParseException e) {
      throw new RuntimeException(e);
    }
  }

  public boolean introspect(String token) {
    try {
      JWSVerifier verifier = new MACVerifier(secret.getBytes());

      SignedJWT signedJWT = SignedJWT.parse(token);

      Date expireTime = signedJWT.getJWTClaimsSet().getExpirationTime();

      return signedJWT.verify(verifier) && expireTime.after(new Date());
    } catch (JOSEException e) {
      log.error("JOSEException occurred: {}", e.getMessage());
      throw new RuntimeException("JOSEException occurred", e);
    } catch (ParseException e) {
      log.error("ParseException occurred: {}", e.getMessage());
      throw new RuntimeException("ParseException occurred", e);
    }
  }
}
