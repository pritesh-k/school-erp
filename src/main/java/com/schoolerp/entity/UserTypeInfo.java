package com.schoolerp.entity;

import com.schoolerp.enums.Role;
import lombok.Builder;

public class UserTypeInfo {
    private Long userId;         // ID of the user
    private Role userType;     // "STUDENT", "TEACHER", "PARENT"
    private Long entityId;       // ID of the specific entity
    private String displayName;  // Full name for display

    private String token;

    private String username;

    private String academicSession;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Role getUserType() {
        return userType;
    }

    public void setUserType(Role userType) {
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

    public String getAcademicSession() {
        return academicSession;
    }

    public void setAcademicSession(String academicSession) {
        this.academicSession = academicSession;
    }
}
