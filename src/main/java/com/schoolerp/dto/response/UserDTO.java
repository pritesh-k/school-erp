package com.schoolerp.dto.response;

import com.schoolerp.dto.BaseDTO;
import com.schoolerp.enums.Role;

import java.time.Instant;

public class UserDTO extends BaseDTO {
    private String username;
    private String email;
    private Role role;
    private boolean enabled = true;

    private String jwtToken;

    private String displayName;

    public UserDTO(Long id, Instant createdAt, Instant updatedAt,
                   Long createdBy, Long updatedBy, boolean deleted,
                   boolean active, String username, String email,
                   Role role, boolean enabled, String jwtTokenm, String displayName) {
        super(id, createdAt, updatedAt, createdBy, updatedBy, deleted, active);
        this.username = username;
        this.email = email;
        this.role = role;
        this.enabled = enabled;
        this.jwtToken = jwtToken;
        this.displayName = displayName;
    }

    public UserDTO(String username, String email, Role role, boolean enabled, String jwtToken) {
        this.username = username;
        this.email = email;
        this.role = role;
        this.enabled = enabled;
        this.jwtToken = jwtToken;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getJwtToken() {
        return jwtToken;
    }

    public void setJwtToken(String jwtToken) {
        this.jwtToken = jwtToken;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
