package com.schoolerp.dto.response.attendance;

import com.schoolerp.enums.AttendanceStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AttendanceResponseDto {
    private Long id;
    private StudentBasicDto student;
    private SectionBasicDto section;
    private LocalDate date;
    private AttendanceStatus status;
    private String remarks;
    private TeacherBasicDto recordedBy;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}