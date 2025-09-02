package com.schoolerp.dto.request;

import com.schoolerp.enums.DutyRole;

public class ExamInvigilationDutyUpdateDto {
    private Long teacherId;
    private DutyRole role; // e.g. "INVIGILATOR", "SUPERVISOR"

    public Long getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(Long teacherId) {
        this.teacherId = teacherId;
    }

    public DutyRole getRole() {
        return role;
    }

    public void setRole(DutyRole role) {
        this.role = role;
    }
}
