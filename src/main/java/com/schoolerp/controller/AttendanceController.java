package com.schoolerp.controller;

import com.schoolerp.dto.request.AttendanceCreateDto;
import com.schoolerp.dto.request.AttendanceUpdateDto;
import com.schoolerp.dto.response.ApiResponse;
import com.schoolerp.dto.response.AttendanceResponseDto;
import com.schoolerp.dto.response.StudentResponseDto;
import com.schoolerp.dto.response.attendance.*;
import com.schoolerp.entity.UserTypeInfo;
import com.schoolerp.enums.AttendanceStatus;
import com.schoolerp.enums.Role;
import com.schoolerp.exception.UnauthorizedException;
import com.schoolerp.service.AttendanceService;
import com.schoolerp.service.RequestContextService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v1/attendance")
@RequiredArgsConstructor
public class AttendanceController {
    private final AttendanceService service;
    private final RequestContextService requestContextService;

    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('TEACHER')")
    @PostMapping("/students/{studentId}/attendances")
    public ApiResponse<AttendanceResponseDto> markAttendance(
            @PathVariable Long studentId,
            @RequestParam AttendanceStatus attendanceStatus,
            @RequestParam(required = false) String remarks,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            HttpServletRequest request) {

        UserTypeInfo userTypeInfo = requestContextService.getCurrentUserContext();

        Long userId = userTypeInfo.getUserId();
        Long entityId = userTypeInfo.getEntityId();
        String role = userTypeInfo.getUserType().name();

        if(role.equals(Role.TEACHER.name()) && entityId == null) {
            throw new UnauthorizedException("Invalid Teacher");
        }
        return ApiResponse.ok(service.markDateAttendance(studentId, attendanceStatus, remarks, entityId, date, userId));
    }
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('TEACHER')")
    @PostMapping("/bulk")
    public ApiResponse<List<AttendanceResponseDto>> markBulk(
            @RequestBody List<AttendanceCreateDto> dtos, HttpServletRequest request) {

        UserTypeInfo userTypeInfo = requestContextService.getCurrentUserContext();

        Long userId = userTypeInfo.getUserId();
        Long entityId = userTypeInfo.getEntityId();
        String role = userTypeInfo.getUserType().name();

        if(role.equals(Role.TEACHER.name()) && entityId == null) {
            throw new UnauthorizedException("Invalid Teacher");
        }
        return ApiResponse.ok(service.markBulk(dtos, entityId, userId));
    }
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('TEACHER')")
    @PutMapping("/{attendanceId}")
    public ApiResponse<AttendanceResponseDto> update(
            @PathVariable Long attendanceId,
            @RequestBody AttendanceUpdateDto dto, HttpServletRequest request)   {

        UserTypeInfo userTypeInfo = requestContextService.getCurrentUserContext();

        Long userId = userTypeInfo.getUserId();
        Long entityId = userTypeInfo.getEntityId();
        String role = userTypeInfo.getUserType().name();

        if(role.equals(Role.TEACHER.name()) && entityId == null) {
            throw new UnauthorizedException("Invalid Teacher");
        }
        return ApiResponse.ok(service.update(attendanceId, dto, entityId, userId));
    }
    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/{attendanceId}")
    public ApiResponse<Void> delete(@PathVariable Long attendanceId, HttpServletRequest request) {
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
    @GetMapping("/students/{studentId}")
    public ApiResponse<List<AttendanceResponseDto>> byStudent(
            @PathVariable Long studentId,
            @RequestParam(required = false, defaultValue = "0") @Min(0) int page,
            @RequestParam(required = false, defaultValue = "10") @Min(1) @Max(100) int size) {

        Pageable pageable = PageRequest.of(page, size, Sort.by("date").descending());
        Page<AttendanceResponseDto> pagedResult = service.byStudentId(studentId, pageable);
        return ApiResponse.paged(pagedResult);
    }

    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('TEACHER')")
    @GetMapping("/dates/{date}")
    public ApiResponse<List<AttendanceResponseDto>> byDate(
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            @RequestParam(required = false, defaultValue = "0") @Min(0) int page,
            @RequestParam(required = false, defaultValue = "10") @Min(1) @Max(100) int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("date").descending());
        Page<AttendanceResponseDto> pagedResult = service.byDate(date, pageable);
        return ApiResponse.paged(pagedResult);
    }
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('TEACHER')")
    @GetMapping("/dates")
    public ApiResponse<List<AttendanceResponseDto>> byDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            @RequestParam(required = false, defaultValue = "0") @Min(0) int page,
            @RequestParam(required = false, defaultValue = "10") @Min(1) @Max(100) int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("date").descending());
        Page<AttendanceResponseDto> pagedResult = service.byDateRange(startDate, endDate, pageable);
        return ApiResponse.paged(pagedResult);
    }

//    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('TEACHER')")
//    @GetMapping("/search")
//    public ApiResponse<List<AttendanceResponseDto>> search(
//            @RequestParam(required = false) Long studentId,
//            @RequestParam(required = false) Long sectionId,
//            @RequestParam(required = false) Long classId,
//            @RequestParam(required = false) AttendanceStatus status, // PRESENT, ABSENT, LATE
//            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
//            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
//            @RequestParam(required = false, defaultValue = "0") @Min(0) int page,
//            @RequestParam(required = false, defaultValue = "10") @Min(1) @Max(100) int size){
//        Pageable pageable = PageRequest.of(page, size, Sort.by("date").descending());
//        Page<AttendanceResponseDto> pagedResult = service.search(studentId, sectionId, classId, status, startDate, endDate, pageable);
//        return ApiResponse.paged(pagedResult);
//    }

    /**
     * 15. Get today's attendance by section ID
     * This endpoint retrieves today's attendance records for a specific section.
     * If no section ID is provided, it returns all today's attendance records.
     */
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('TEACHER')")
    @GetMapping("/today")
    public ApiResponse<List<AttendanceResponseDto>> todayAttendanceBySectionId(
            @RequestParam(required = false) Long sectionId,
            @RequestParam(required = false, defaultValue = "0") @Min(0) int page,
            @RequestParam(required = false, defaultValue = "10") @Min(1) @Max(100) int size){
        Pageable pageable = PageRequest.of(page, size, Sort.by("date").descending());
        Page<AttendanceResponseDto> pagedResult = service.bySectionId(sectionId, pageable);
        return ApiResponse.paged(pagedResult);
    }
}