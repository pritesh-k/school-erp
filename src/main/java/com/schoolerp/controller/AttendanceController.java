package com.schoolerp.controller;

import com.schoolerp.dto.request.AttendanceCreateDto;
import com.schoolerp.dto.request.AttendanceUpdateDto;
import com.schoolerp.dto.response.ApiResponse;
import com.schoolerp.dto.response.AttendancePercentageReportDto;
import com.schoolerp.dto.response.AttendanceResponseDto;
import com.schoolerp.dto.response.AttendanceSummaryDto;
import com.schoolerp.dto.response.attendance.ClassAttendanceSummaryDto;
import com.schoolerp.dto.response.attendance.SectionAttendanceSummaryDto;
import com.schoolerp.entity.UserTypeInfo;
import com.schoolerp.enums.AttendanceStatus;
import com.schoolerp.enums.Role;
import com.schoolerp.exception.UnauthorizedException;
import com.schoolerp.service.AttendanceService;
import com.schoolerp.service.RequestContextService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v1/attendance")
@RequiredArgsConstructor
public class AttendanceController {
    private final AttendanceService service;
    private final RequestContextService requestContextService;

    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('TEACHER')")
    @PostMapping
    public ApiResponse<AttendanceResponseDto> create(@Valid @RequestBody AttendanceCreateDto req) {
        UserTypeInfo userTypeInfo = requestContextService.getCurrentUserContext();
        Long recordedByUserId = userTypeInfo.getUserId();
        String academicSessionName = userTypeInfo.getAcademicSession();

        AttendanceResponseDto dto = service.createAttendance(academicSessionName, req, recordedByUserId);
        return ApiResponse.ok(dto);
    }
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('TEACHER')")
    @PutMapping("/{attendanceId}")
    public ApiResponse<AttendanceResponseDto> update(
            @PathVariable Long attendanceId,
            @RequestBody AttendanceUpdateDto dto)   {

        UserTypeInfo userTypeInfo = requestContextService.getCurrentUserContext();

        Long userId = userTypeInfo.getUserId();
        Long entityId = userTypeInfo.getEntityId();

        return ApiResponse.ok(service.update(attendanceId, dto, entityId, userId));
    }
    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/{attendanceId}")
    public ApiResponse<Void> delete(@PathVariable Long attendanceId) {
        UserTypeInfo userTypeInfo = requestContextService.getCurrentUserContext();

        Long userId = userTypeInfo.getUserId();
        Long entityId = userTypeInfo.getEntityId();
        String role = userTypeInfo.getUserType().name();

        if(role.equals(Role.TEACHER.name()) && entityId == null) {
            throw new UnauthorizedException("Invalid Teacher");
        }
        service.delete(attendanceId, entityId, userId, role);
        return ApiResponse.ok(null);
    }
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('TEACHER')")
    @GetMapping("/search")
    public ApiResponse<List<AttendanceResponseDto>> search(
            @RequestParam(required = false) Long studentId,
            @RequestParam(required = false) Long sectionId,
            @RequestParam(required = false) Long classId,
            @RequestParam(required = false) AttendanceStatus status,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "10") int size) {

        UserTypeInfo userTypeInfo = requestContextService.getCurrentUserContext();
        String academicSessionName = userTypeInfo.getAcademicSession();

        Pageable pageable = PageRequest.of(page, size, Sort.by("date").descending());
        Page<AttendanceResponseDto> pagedResult = service.search(academicSessionName, studentId, sectionId, classId, status, startDate, endDate, pageable);
        return ApiResponse.paged(pagedResult);
    }

    // Existing detailed report API
    @GetMapping("/report")
    public ApiResponse<List<AttendanceSummaryDto>> getAttendanceReport(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            @RequestParam(required = false) Long classId,
            @RequestParam(required = false) Long sectionId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        UserTypeInfo userTypeInfo = requestContextService.getCurrentUserContext();
        Pageable pageable = PageRequest.of(page, size);
        String academicSessionName = userTypeInfo.getAcademicSession();

        Page<AttendanceSummaryDto> report = service.getAttendanceReport(
                academicSessionName, date, classId, sectionId, pageable);

        return ApiResponse.paged(report);
    }

    @GetMapping("/today/overall-percentage")
    public ApiResponse<Double> getTodayAttendancePercentage() {
        UserTypeInfo userTypeInfo = requestContextService.getCurrentUserContext();
        String academicSessionName = userTypeInfo.getAcademicSession();

        double overallPercentage = service.getTodayAttendancePercentage(academicSessionName);
        return ApiResponse.ok(overallPercentage);
    }
}