package com.schoolerp.dto.response;

import com.schoolerp.dto.BaseDTO;
import com.schoolerp.entity.Section;
import com.schoolerp.entity.Student;
import com.schoolerp.entity.Teacher;
import com.schoolerp.enums.AttendanceStatus;

import java.time.LocalDate;

public class AttendanceResponseDto extends BaseDTO {

    private LocalDate date;
    private AttendanceStatus status;
    private String remarks;

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

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
