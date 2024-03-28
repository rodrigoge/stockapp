package com.stockapp.userservice.services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.stockapp.userservice.db.User;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
@Log4j2
public class TokenService {

    @Value("${token.signing.key}")
    private String tokenSecretKey;

    public String generateToken(String requestID, User user) {
        log.info("[RequestID: {}] Entering generate token flow to login in app.", requestID);
        var algorithm = Algorithm.HMAC512(tokenSecretKey);
        var expirationDate = LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
        return JWT
                .create()
                .withIssuer("stock-app")
                .withSubject(user.getEmail())
                .withExpiresAt(expirationDate)
                .sign(algorithm);
    }

    public String validateToken(String token) {
        log.info("Entering validate token flow to login in app.");
        Algorithm algorithm = Algorithm.HMAC256(tokenSecretKey);
        return JWT.require(algorithm)
                .withIssuer("stock-app")
                .build()
                .verify(token)
                .getSubject();
    }
}
