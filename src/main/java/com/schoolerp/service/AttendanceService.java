package com.schoolerp.service;

import com.schoolerp.dto.request.AttendanceCreateDto;
import com.schoolerp.dto.request.AttendanceUpdateDto;
import com.schoolerp.dto.response.AttendancePercentageReportDto;
import com.schoolerp.dto.response.AttendanceResponseDto;
import com.schoolerp.enums.AttendanceStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;

public interface AttendanceService {

    List<AttendanceResponseDto> markBulk(List<AttendanceCreateDto> dtos, Long entityId, Long userId);

    AttendanceResponseDto get(Long id);
    Page<AttendanceResponseDto> list(Pageable pageable);
    AttendanceResponseDto update(Long id, AttendanceUpdateDto dto, Long entityId, Long userId);

    void delete(Long attendanceId, Long entityId, Long userId, String role);

    Page<AttendanceResponseDto> search(String academicSessionName,
                                       Long studentId,
                                       Long sectionId,
                                       Long classId,
                                       AttendanceStatus status,
                                       LocalDate startDate,
                                       LocalDate endDate,
                                       Pageable pageable);

    AttendanceResponseDto createAttendance(String academicSessionName,
                                           AttendanceCreateDto req,
                                           Long recordedByUserId);

    AttendancePercentageReportDto getAttendancePercentageReport(String academicSessionName);
}