package identityservice.security;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import identityservice.entity.User;
import identityservice.exception.AuthenticationException;
import identityservice.repository.InvalidatedTokenRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.StringJoiner;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class JwtService {

  private final InvalidatedTokenRepository invalidatedTokenRepository;

  @Value("${jwt.secret}")
  private String secret;

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
    long expireTimeAccessToken = Instant.now().plus(1, ChronoUnit.HOURS).toEpochMilli();
    long expireTimeRefreshToken = Instant.now().plus(24, ChronoUnit.HOURS).toEpochMilli();

    JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);

    JWTClaimsSet.Builder claimsSet = new JWTClaimsSet.Builder()
            .subject(userDetails.getUsername())
            .issuer("vantruong.com")
            .issueTime(new Date())
            .jwtID(UUID.randomUUID().toString())
            .expirationTime(new Date(isAccessToken ? expireTimeAccessToken : expireTimeRefreshToken));

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

  public boolean validateToken(String token) {
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

  public SignedJWT verifyToken(String token) throws JOSEException, ParseException {
    JWSVerifier verifier = new MACVerifier(secret.getBytes());
    SignedJWT signedJWT = SignedJWT.parse(token);
    Date expireTime = signedJWT.getJWTClaimsSet().getExpirationTime();

    var verified = signedJWT.verify(verifier);

    if (!(verified && expireTime.after(new Date()))) {
      throw new AuthenticationException();
    }

    if (invalidatedTokenRepository.existsById(signedJWT.getJWTClaimsSet().getJWTID())) {
      throw new AuthenticationException();
    }
    return signedJWT;
  }
}
