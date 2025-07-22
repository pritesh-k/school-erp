package com.schoolerp.service;

import com.schoolerp.entity.UserTypeInfo;
import com.schoolerp.exception.UnauthorizedException;
import com.schoolerp.security.JwtUtil;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Data;
import org.springframework.stereotype.Service;

@Service
public class RequestContextService {

    public UserTypeInfo getCurrentUserContext(HttpServletRequest request) {
        try {
            Long userId = JwtUtil.getUserIdFromRequest(request);
            String role = JwtUtil.getRoleFromRequest(request);
            Long entityId = JwtUtil.getEntityIdFromRequest(request);
            String token = JwtUtil.getTokenFromRequest(request);

            if (userId == null || role == null || token == null) {
                throw new UnauthorizedException("Invalid or missing token details");
            }

            UserTypeInfo userTypeInfo = new UserTypeInfo();
            userTypeInfo.setUserId(userId);
            userTypeInfo.setUserType(role);
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

