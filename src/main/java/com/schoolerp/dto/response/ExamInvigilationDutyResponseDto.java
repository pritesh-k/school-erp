package com.schoolerp.dto.response;

import com.schoolerp.dto.BaseDTO;

public class ExamInvigilationDutyResponseDto extends BaseDTO {
    private Long examSlotId;
    private Long teacherId;
    private String role;

    public Long getExamSlotId() {
        return examSlotId;
    }

    public void setExamSlotId(Long examSlotId) {
        this.examSlotId = examSlotId;
    }

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
