package com.schoolerp.service;

import com.schoolerp.entity.UserTypeInfo;
import com.schoolerp.security.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Data;
import org.springframework.stereotype.Service;

@Service
public class RequestContextService {

    public UserTypeInfo getCurrentUserContext(HttpServletRequest request) {
        UserTypeInfo userTypeInfo = new UserTypeInfo();
        userTypeInfo.setUserId(JwtUtil.getUserIdFromRequest(request));
        userTypeInfo.setUserType(JwtUtil.getRoleFromRequest(request));
        userTypeInfo.setEntityId(JwtUtil.getEntityIdFromRequest(request));
        userTypeInfo.setToken(JwtUtil.getTokenFromRequest(request));
        return userTypeInfo;
    }
}

