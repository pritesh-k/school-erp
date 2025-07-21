package com.schoolerp.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.time.Instant;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final UserDetailsService userDetailsService;
    public static final String JWT_TOKEN_ATTRIBUTE = "JWT_TOKEN";
    public static final String USER_ID_ATTRIBUTE = "USER_ID";
    public static final String ENTITY_ID_ATTRIBUTE = "ENTITY_ID";
    public static final String USER_ROLE_ATTRIBUTE = "USER_ROLE";

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        if (isPublicEndpoint(request.getRequestURI())) {
            filterChain.doFilter(request, response);
            return;
        }

        try {
            String header = request.getHeader(HttpHeaders.AUTHORIZATION);

            if (header != null && header.startsWith("Bearer ") && header.length() > 7) {
                String token = header.substring(7);
                String username = jwtUtil.extractUsername(token);

                if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                    authenticateUser(token, username, request);

                    // Store token and claims in request attributes
                    storeTokenDataInRequest(request, token);
                }
            }

        } catch (ExpiredJwtException e) {
            log.warn("JWT token expired for request: {}", request.getRequestURI());
            handleAuthError(response, "Token expired");
            return;

        } catch (MalformedJwtException e) {
            log.warn("Invalid JWT token for request: {}", request.getRequestURI());
            handleAuthError(response, "Invalid token");
            return;

        } catch (SignatureException e) {
            log.warn("JWT signature validation failed for request: {}", request.getRequestURI());
            handleAuthError(response, "Invalid token signature");
            return;

        } catch (UsernameNotFoundException e) {
            log.warn("User not found: {}", e.getMessage());
            handleAuthError(response, "User not found");
            return;

        } catch (Exception e) {
            log.error("Authentication error for request: {}", request.getRequestURI(), e);
            handleAuthError(response, "Authentication failed");
            return;
        }

        filterChain.doFilter(request, response);
    }

    private void storeTokenDataInRequest(HttpServletRequest request, String token) {
        try {
            // Store the token
            request.setAttribute(JWT_TOKEN_ATTRIBUTE, token);

            // Extract and store claims
            Long userId = jwtUtil.extractUserId(token);
            String role = jwtUtil.extractRole(token);
            Long entityId = jwtUtil.extractEntityId(token);

            request.setAttribute(USER_ID_ATTRIBUTE, userId);
            request.setAttribute(USER_ROLE_ATTRIBUTE, role);
            request.setAttribute(ENTITY_ID_ATTRIBUTE, entityId); // null for ADMIN/PRINCIPAL

        } catch (Exception e) {
            log.warn("Failed to extract token claims", e);
        }
    }

    private void authenticateUser(String token, String username, HttpServletRequest request) {
        UserDetails user = userDetailsService.loadUserByUsername(username);

        if (jwtUtil.validate(token, user)) {
            UsernamePasswordAuthenticationToken auth =
                    new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
            auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(auth);
        }
    }

    private boolean isPublicEndpoint(String path) {
        return path.startsWith("/api/v1/auth/") ||
                path.equals("/health") ||
                path.startsWith("/actuator/");
    }

    private void handleAuthError(HttpServletResponse response, String message) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        String jsonResponse = String.format(
                "{\"success\":false,\"message\":\"%s\",\"timestamp\":\"%s\"}",
                message, Instant.now()
        );

        response.getWriter().write(jsonResponse);
    }
}
