package com.schoolerp.dto.response.attendance;

public interface AttendanceStatsByStudentDto {
    Long getStudentId();
    Long getTotal();
    Long getPresent();
    Long getAbsent();
    Long getLate();
    Long getLeaveCount();
}

