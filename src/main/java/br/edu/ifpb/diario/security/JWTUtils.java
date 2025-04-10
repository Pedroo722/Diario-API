package br.edu.ifpb.diario.security;

import java.util.Date;

import javax.crypto.SecretKey;

import br.edu.ifpb.diario.exceptions.JwtTokenException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.SignatureException;

@Component
public class JWTUtils {

    @Value("${JWT.secret.key}")
    private String secretKey;

    private final long jwtExpirationMs = 86400000; // 1 dia

    private SecretKey getSecretKey() {
        return Keys.hmacShaKeyFor(secretKey.getBytes());
    }

    public String generateJwtToken(String username) {
        return Jwts.builder()
                .subject(username)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + jwtExpirationMs))
                .signWith(getSecretKey())
                .compact();
    }

    public boolean validateJwtToken(String authToken) throws JwtTokenException {
        try {
            Jwts.parser()
                    .verifyWith(getSecretKey())
                    .build()
                    .parseSignedClaims(authToken);
            return true;
        } catch (Exception e) {
            throw new JwtTokenException("Token inválido ou expirado!");
        }
    }

    public String getUserNameFromJwtToken(String token) throws JwtTokenException {
        try {
            return Jwts.parser()
                    .verifyWith(getSecretKey())
                    .build()
                    .parseSignedClaims(token)
                    .getPayload()
                    .getSubject();
        } catch (ExpiredJwtException ex) {
            throw new JwtTokenException("Token expirado! É preciso fazer login novamente.");
        } catch (UnsupportedJwtException | MalformedJwtException
                 | SignatureException ex) {
            throw new JwtTokenException("Token inválido!");
        } catch (Exception ex) {
            throw new JwtTokenException("Erro de autenticação: " + ex.getMessage());
        }
    }
}