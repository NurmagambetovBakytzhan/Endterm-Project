package kz.kbtu.sf.orderservice.jwt;

import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Component
public class JwtTokenUtil {

    @Value("${app.jwt.secret}")
    private String SECRET_KEY;

    public String generateAccessToken(UUID userId, String email, List<String> roles) {
        long EXPIRATION_TIME = 1000 * 60 * 60;
        return Jwts.builder()
                .setSubject(userId + "," + email)
                .claim("roles", roles)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }

    public boolean validateAccessToken(String token) {
        try {
            Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException ex) {
            return false;
        }
    }

    public Claims parseClaims(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();
    }

    public UUID extractUserId(String token) {
        Claims claims = parseClaims(token);
        String subject = claims.getSubject();
        String[] parts = subject.split(",");
        return UUID.fromString(parts[0]);
    }

    public String extractEmail(String token) {
        Claims claims = parseClaims(token);
        String subject = claims.getSubject();
        String[] parts = subject.split(",");
        return parts[1];
    }

    public List<String> extractRoles(String token) {
        Claims claims = parseClaims(token);
        return (List<String>) claims.get("roles");
    }
}
