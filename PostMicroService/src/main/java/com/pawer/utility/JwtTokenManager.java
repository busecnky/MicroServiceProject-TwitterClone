package com.pawer.utility;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.pawer.exception.EErrorType;
import com.pawer.exception.PostException;
import org.springframework.web.bind.annotation.ControllerAdvice;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@ControllerAdvice
public class JwtTokenManager {
    private final String sifreAnahtari = "#luC}VB>IsC)*>&x**zMqIdD}Pct_%T3w>{9&Zl$tbXZwfF3J+p%iD~o]8-!^`;";
    private final Long exTime = 1000L * 60 * 30; // token gecerlilik süresi: 30 dk

    public Optional<String> createToken(Long id) {
        String token = "";
        try {
            token = JWT.create().withAudience()
                    .withClaim("id", id)
                    .withIssuer("pawer")
                    .withIssuedAt(new Date())
                    .withExpiresAt(new Date(System.currentTimeMillis() + exTime))
                    .sign(Algorithm.HMAC512(sifreAnahtari));
            return Optional.of(token);
        } catch (Exception exception) {
            return Optional.empty();
        }
    }
    public Optional<Long> validToken(String token){
        if(token.startsWith("\"token\":")) {
           String[] newToken = token.split("\"");
            token = newToken[3];
        }

            try {
                Algorithm algorithm = Algorithm.HMAC512(sifreAnahtari);
                JWTVerifier verifier = JWT.require(algorithm).withIssuer("pawer").build();
                DecodedJWT decodedJWT = verifier.verify(token);
                if (decodedJWT == null) return Optional.empty();
                return Optional.of(decodedJWT.getClaim("id").asLong());


            } catch (Exception e) {
                throw new PostException(EErrorType.INVALID_TOKEN);
            }

    }

}
