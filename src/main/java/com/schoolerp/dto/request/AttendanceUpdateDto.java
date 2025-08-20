package com.schoolerp.dto.request;

import com.schoolerp.enums.AttendanceStatus;
import jakarta.validation.constraints.Size;

public class AttendanceUpdateDto {
    private AttendanceStatus status;

    public AttendanceStatus getStatus() {
        return status;
    }

    public void setStatus(AttendanceStatus status) {
        this.status = status;
    }
}
