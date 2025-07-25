package com.schoolerp.service;

import com.schoolerp.entity.UserTypeInfo;
import com.schoolerp.enums.Role;
import com.schoolerp.exception.UnauthorizedException;
import com.schoolerp.security.JwtUtil;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Service
public class RequestContextService {
    @Autowired
    private JwtUtil jwtUtil;

    public UserTypeInfo getCurrentUserContext() {
        try {
            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            if (attributes == null) {
                throw new UnauthorizedException("No request context available");
            }

            HttpServletRequest request = attributes.getRequest();

            String token = jwtUtil.getTokenFromRequest(request);
            Long userId = jwtUtil.extractUserId(token);
            String role = jwtUtil.extractRole(token);
            Long entityId = jwtUtil.extractEntityId(token);

            if (userId == null || role == null || token == null) {
                throw new UnauthorizedException("Invalid or missing token details");
            }

            UserTypeInfo userTypeInfo = new UserTypeInfo();
            userTypeInfo.setUserId(userId);
            userTypeInfo.setUserType(Role.fromString(role));
            userTypeInfo.setEntityId(entityId);
            userTypeInfo.setToken(token);
            return userTypeInfo;

        } catch (ExpiredJwtException e) {
            throw new UnauthorizedException("Token expired", e);
        } catch (MalformedJwtException e) {
            throw new UnauthorizedException("Malformed token", e);
        } catch (JwtException e) {
            throw new UnauthorizedException("Invalid token", e);
        } catch (Exception e) {
            throw new UnauthorizedException("Unable to extract token claims", e);
        }
    }

}

