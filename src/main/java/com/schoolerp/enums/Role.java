package com.schoolerp.enums;
public enum Role {
    ADMIN, TEACHER, STUDENT, PARENT, PRINCIPAL;

    public static Role fromString(String roleStr) {
        if (roleStr == null || roleStr.trim().isEmpty()) {
            throw new IllegalArgumentException("Role string is null or empty");
        }
        try {
            return Role.valueOf(roleStr.trim().toUpperCase());
        } catch (IllegalArgumentException ex) {
            throw new IllegalArgumentException("Invalid role: " + roleStr, ex);
        }
    }
}
