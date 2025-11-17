package br.com.treinaweb.twjobs.core.services.jwt;

import java.time.Instant;
import java.util.Date;
import java.nio.charset.StandardCharsets;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Service;

import br.com.treinaweb.twjobs.config.JwtConfigProperties;
import br.com.treinaweb.twjobs.core.exceptions.JwtServiceException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class JjwtJwtService implements JwtService {

    private final JwtConfigProperties configProperties;

    @Override
    public String generateAccessToken(String sub) {
        return generateToken(
            sub, 
            configProperties.getAccessExpiresIn(), 
            configProperties.getAccessSecret()
        );
    }

    @Override
    public String getSubFromAccessToken(String token) {
        return getSubFromToken(token, configProperties.getAccessSecret());
    }

    @Override
    public String generateRefreshToken(String sub) {
        return generateToken(
            sub, 
            configProperties.getRefreshExpiresIn(), 
            configProperties.getRefreshSecret()
        );
    }

    @Override
    public String getSubFromRefreshToken(String token) {
        return getSubFromToken(token, configProperties.getRefreshSecret());
    }

    private String generateToken(String sub, long expiresIn, String secret) {
        var now = Instant.now();
        var expiration = now.plusSeconds(expiresIn);
        var key = createKey(secret);
        return Jwts.builder()
            .subject(sub)
            .issuedAt(Date.from(now))
            .expiration(Date.from(expiration))
            .signWith(key)
            .compact();
    }

    private String getSubFromToken(String token, String secret) {
        var key = createKey(secret);
        try {
            return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
        } catch (JwtException e) {
            throw new JwtServiceException(e.getLocalizedMessage());
        }
    }

    private SecretKey createKey(String secret) {
        return Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }
    
}
