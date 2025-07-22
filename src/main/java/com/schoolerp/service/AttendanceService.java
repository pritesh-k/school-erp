package com.schoolerp.service;

import com.schoolerp.dto.request.AttendanceCreateDto;
import com.schoolerp.dto.request.AttendanceUpdateDto;
import com.schoolerp.dto.response.AttendanceResponseDto;
import com.schoolerp.dto.response.attendance.AttendancePercentageDto;
import com.schoolerp.dto.response.attendance.AttendanceSummaryDto;
import com.schoolerp.dto.response.attendance.MonthlyAttendanceReportDto;
import com.schoolerp.dto.response.attendance.WeeklyAttendanceReportDto;
import com.schoolerp.enums.AttendanceStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

public interface AttendanceService {


    List<AttendanceResponseDto> markBulk(List<AttendanceCreateDto> dtos, Long entityId, Long userId);

    AttendanceResponseDto get(Long id);
    Page<AttendanceResponseDto> list(Pageable pageable);
    Page<AttendanceResponseDto> byStudentId(Long studentId, Pageable pageable);
    Page<AttendanceResponseDto> bySectionAndDate(Long sectionId, LocalDate date, Pageable pageable);

    @Transactional(readOnly = true)
    Page<AttendanceResponseDto> bySectionId(Long sectionId, Pageable pageable);

    @Transactional(readOnly = true)
    Page<AttendanceResponseDto> byDate(LocalDate date, Pageable pageable);

    @Transactional(readOnly = true)
    Page<AttendanceResponseDto> byDateRange(LocalDate startDate, LocalDate endDate, Pageable pageable);

    @Transactional(readOnly = true)
    AttendanceSummaryDto getStudentSummary(Long studentId, LocalDate startDate, LocalDate endDate);

    @Transactional(readOnly = true)
    AttendanceSummaryDto getSectionSummary(Long sectionId, LocalDate startDate, LocalDate endDate);

    @Transactional(readOnly = true)
    AttendancePercentageDto getStudentPercentage(Long studentId, LocalDate startDate, LocalDate endDate);

    AttendanceResponseDto update(Long id, AttendanceUpdateDto dto, Long entityId, Long userId);

    void delete(Long id);

    List<AttendanceResponseDto> markAllPresent(Long classId, LocalDate date, Long teacherId);

    Page<AttendanceResponseDto> byClassId(Long classId, Pageable pageable);

    List<AttendanceResponseDto> byClassAndDate(Long classId, LocalDate date);

    AttendancePercentageDto getAttendancePercentageByStudent(Long studentId,
                                                             LocalDate startDate,
                                                             LocalDate endDate);

    Page<AttendanceResponseDto> search(Long studentId,
                                       Long sectionId,
                                       Long classId,
                                       AttendanceStatus status,
                                       LocalDate startDate,
                                       LocalDate endDate,
                                       Pageable pageable);

    Page<AttendanceResponseDto> getTodayAttendance(Long classId, Pageable pageable);

    MonthlyAttendanceReportDto getMonthlyReport(int year, int month,
                                                Long classId,
                                                Long studentId);

    WeeklyAttendanceReportDto getWeeklyReport(LocalDate weekStartDate,
                                              Long classId,
                                              Long studentId);

    AttendanceResponseDto markDateAttendance(Long studentId, AttendanceStatus attendanceStatus,
                                             String remarks, Long teacherId, LocalDate date, Long userId);
}