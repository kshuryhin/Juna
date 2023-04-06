package ua.pp.juna.gatewayservice.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.*;
import io.jsonwebtoken.impl.TextCodec;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;
import ua.pp.juna.gatewayservice.exception.JwtTokenMalformedException;
import ua.pp.juna.gatewayservice.exception.JwtTokenMissingException;

import javax.naming.AuthenticationException;
import java.io.IOException;
import java.security.Key;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Component
public class JwtUtil {
    private static final String SECRET_KEY = "404E635266556A586E3272357538782F413F4428472B4B6250645367566B5970";

    public Claims getClaims(final String token) {
        try {
            final var body = Jwts.parser().setSigningKey(getSignInKey()).parseClaimsJws(token).getBody();
            return body;
        } catch (Exception e) {
            System.out.println(e.getMessage() + " => " + e);
        }
        return null;
    }

    public String getSubject(final String token) {
        final var subject = Jwts.parser().setSigningKey(getSignInKey()).parseClaimsJws(token).getBody().getSubject();
        return subject;
    }

    public String getSubjectWithoutExpirationCheck(final String token) {
        final var parts = token.split("\\.");
        if (parts.length != 3) {
            throw new IllegalArgumentException("Invalid JWT token format");
        }

        final var payloadJson = new String(TextCodec.BASE64URL.decode(parts[1]));
        final var objectMapper = new ObjectMapper();
        try {
            final var payloadMap = objectMapper.readValue(payloadJson, Map.class);
            return (String) payloadMap.get("sub");
        } catch (IOException e) {
            throw new IllegalStateException("Error while parsing JWT payload", e);
        }
    }


    public void validateRoles(final String token, final Set<String> roles) throws AuthenticationException {
        final var body = Jwts.parser().setSigningKey(getSignInKey()).parseClaimsJws(token).getBody();
        final var authorities = ((List<List<LinkedHashMap<String, String>>>) (body.get("roles"))).get(0).get(0).values();
        authorities.stream()
                .filter(role -> roles.contains(role))
                .findFirst()
                .orElseThrow(AuthenticationException::new);


    }

    public void validateToken(final String token) throws JwtTokenMalformedException, JwtTokenMissingException {
        try {
            Jwts.parser().setSigningKey(getSignInKey()).parseClaimsJws(token);
        } catch (SignatureException ex) {
            throw new JwtTokenMalformedException("Invalid JWT signature");
        } catch (MalformedJwtException ex) {
            throw new JwtTokenMalformedException("Invalid JWT token");
        } catch (ExpiredJwtException ex) {
            throw new JwtTokenMalformedException("Expired JWT token");
        } catch (UnsupportedJwtException ex) {
            throw new JwtTokenMalformedException("Unsupported JWT token");
        } catch (IllegalArgumentException ex) {
            throw new JwtTokenMissingException("JWT claims string is empty.");
        }
    }

    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
