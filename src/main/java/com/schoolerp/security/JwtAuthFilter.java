package com.schoolerp.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
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
            String academicSession = request.getHeader(jwtUtil.ACADEMIC_SESSION_ID);
            if (header != null && header.startsWith("Bearer ") && header.length() > 7) {
                String token = header.substring(7);
                request.setAttribute(jwtUtil.JWT_TOKEN_ATTRIBUTE, token);    // <<< ADD THIS

                String username = jwtUtil.extractUsername(token);

                if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                    authenticateUser(token, username, request);
                }
            }

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
        return path.startsWith("/api/v1/auth/register") || path.startsWith("/api/v1/auth/login") ||
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
