package com.schoolerp.dto.response;

import com.schoolerp.entity.UserTypeInfo;
import com.schoolerp.enums.Role;

public class AuthResponse {
    private UserTypeInfo userInfo;

    public AuthResponse(UserTypeInfo userInfo) {
        this.userInfo = userInfo;
    }

    public UserTypeInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserTypeInfo userInfo) {
        this.userInfo = userInfo;
    }
}