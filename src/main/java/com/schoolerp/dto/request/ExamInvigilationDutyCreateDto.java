package com.schoolerp.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class ExamInvigilationDutyCreateDto {
    @NotNull
    private Long teacherId;
    @NotBlank
    private String role; // e.g. "INVIGILATOR", "SUPERVISOR"

    public Long getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(Long teacherId) {
        this.teacherId = teacherId;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
