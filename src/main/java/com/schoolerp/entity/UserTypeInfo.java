package com.schoolerp.entity;

import lombok.Builder;

public class UserTypeInfo {
    private Long userId;         // ID of the user
    private String userType;     // "STUDENT", "TEACHER", "PARENT"
    private Long entityId;       // ID of the specific entity
    private String displayName;  // Full name for display

    private String token;

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public Long getEntityId() {
        return entityId;
    }

    public void setEntityId(Long entityId) {
        this.entityId = entityId;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Long getUserId() {
        return userId;
    }
    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
