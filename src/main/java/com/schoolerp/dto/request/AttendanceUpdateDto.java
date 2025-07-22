package com.schoolerp.dto.request;

import com.schoolerp.enums.AttendanceStatus;
import jakarta.validation.constraints.Size;

public class AttendanceUpdateDto {
    private AttendanceStatus status;

    @Size(max = 200, message = "Remarks cannot exceed 500 characters")
    private String remarks;

    public AttendanceStatus getStatus() {
        return status;
    }

    public void setStatus(AttendanceStatus status) {
        this.status = status;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
}
