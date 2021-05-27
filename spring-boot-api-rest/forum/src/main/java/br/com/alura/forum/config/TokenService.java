package br.com.alura.forum.config;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.Instant;
import java.time.temporal.TemporalUnit;

@Service
public class TokenService {

    private String secret;
    private String expiration;

    public TokenService( @Value("${forum.jwt.secret}") String secret,
                         @Value("${forum.jwt.expiration}") String expiration) {

        this.secret = secret;
        this.expiration = expiration;
    }

    public String criar(Authentication authentication) {
        Instant agora = Instant.now();
        Instant expirationInstant = agora.plusMillis(Long.parseLong(expiration));
        return Jwts.builder()
                .setIssuer("API - Forum da Alura")
                .setSubject(authentication.getName())
                .setIssuedAt(Date.from(agora))
                .setExpiration(Date.from(expirationInstant))
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }
}
