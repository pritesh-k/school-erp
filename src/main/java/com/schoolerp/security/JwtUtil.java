package com.schoolerp.security;

import com.schoolerp.entity.UserTypeInfo;
import com.schoolerp.enums.Role;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.function.Function;

@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private Long expiration;

    public final String JWT_TOKEN_ATTRIBUTE = "JWT_TOKEN";
    public final String ACADEMIC_SESSION_ID = "X-Academic-Session";

    private SecretKey key() { return Keys.hmacShaKeyFor(secret.getBytes()); }

    public String generateToken(UserTypeInfo user) {
        JwtBuilder builder = Jwts.builder()
                .subject(user.getUsername())
                .claim("role", user.getUserType())
                .claim("userId", user.getUserId())
                .claim("username", user.getUsername())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(key());

        // Only add entityId for roles that have it (not ADMIN or PRINCIPAL)
        if (hasEntityId(user.getUserType()) && user.getEntityId() != null) {
            builder.claim("entityId", user.getEntityId());
        }

        return builder.compact();
    }

    private boolean hasEntityId(Role role) {
        return role == Role.STUDENT ||
                role == Role.TEACHER ||
                role == Role.PARENT;
    }

    public String extractUsername(String token) {
        return Jwts.parser().verifyWith(key()).build()
                .parseSignedClaims(token).getPayload().getSubject();
    }

    public boolean validate(String token, UserDetails user) {
        return user.getUsername().equals(extractUsername(token)) && !isExpired(token);
    }

    private boolean isExpired(String token) {
        return Jwts.parser().verifyWith(key()).build()
                .parseSignedClaims(token).getPayload().getExpiration().before(new Date());
    }

    // Extract user ID
    public Long extractUserId(String token) {
        return extractClaim(token, claims -> {
            Object userId = claims.get("userId");
            if (userId instanceof Integer) {
                return ((Integer) userId).longValue();
            }
            return (Long) userId;
        });
    }

    // Extract role as string
    public String extractRole(String token) {
        return extractClaim(token, claims -> claims.get("role", String.class));
    }


    // Extract entity ID (null for ADMIN/PRINCIPAL)
    public Long extractEntityId(String token) {
        return extractClaim(token, claims -> {
            Object entityId = claims.get("entityId");
            if (entityId == null) {
                return null; // Normal for ADMIN/PRINCIPAL
            }
            if (entityId instanceof Integer) {
                return ((Integer) entityId).longValue();
            }
            return (Long) entityId;
        });
    }

    // Generic method to extract any claim
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    // Extract all claims
    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(key())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public String getTokenFromRequest(HttpServletRequest request) {
        return (String) request.getAttribute(JWT_TOKEN_ATTRIBUTE);
    }
    public String getAcademicSession(HttpServletRequest request) {
        return (String) request.getAttribute(ACADEMIC_SESSION_ID);
    }
}