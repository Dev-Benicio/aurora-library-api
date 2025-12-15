package com.auroralibrary.library.service;

import com.auroralibrary.library.model.Administrator;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;

@Service
public class TokenService {

  @Value(value = "${api.security.token.secret}")
  private String secret;

  public String generateToken(Administrator admin) {
    try {
      Algorithm algorithm = Algorithm.HMAC256(secret);
      return JWT.create()
          .withIssuer("API Aurora")
          .withSubject(admin.getLogin())
          .withExpiresAt(getExpiration())
          .sign(algorithm);
    } catch (JWTCreationException exception){
        throw new RuntimeException(exception);
    }
  }

  private Instant getExpiration() {
    return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
  }

  public String getToken(String token) {
    try {
      Algorithm algorithm = Algorithm.HMAC256(secret);
      return JWT.require(algorithm)
        .withIssuer("API Aurora")
        .build()
        .verify(token)
        .getSubject();
    } catch (JWTVerificationException exception){
        throw new RuntimeException("Token inválido ou expirado");
    }
  }
}