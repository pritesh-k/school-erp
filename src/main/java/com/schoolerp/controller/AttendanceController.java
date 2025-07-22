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

    // 1. Mark today's attendance for a student
    @PostMapping("/students/{studentId}/attendances")
    public ApiResponse<AttendanceResponseDto> markAttendance(
            @PathVariable Long studentId,
            @RequestParam AttendanceStatus attendanceStatus,
            @RequestParam(required = false) String remarks,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            HttpServletRequest request) {

        UserTypeInfo userTypeInfo = requestContextService.getCurrentUserContext(request);

        Long userId = userTypeInfo.getUserId();
        Long entityId = userTypeInfo.getEntityId();
        String role = userTypeInfo.getUserType();

        if(role.equals(Role.TEACHER.name()) && entityId == null) {
            throw new UnauthorizedException("Invalid Teacher");
        }
        return ApiResponse.ok(service.markDateAttendance(studentId, attendanceStatus, remarks, entityId, date, userId));
    }


    // 3. Mark bulk attendance
    @PostMapping("/bulk")
    public ApiResponse<List<AttendanceResponseDto>> markBulk(
            @RequestBody List<AttendanceCreateDto> dtos, HttpServletRequest request) {

        UserTypeInfo userTypeInfo = requestContextService.getCurrentUserContext(request);

        Long userId = userTypeInfo.getUserId();
        Long entityId = userTypeInfo.getEntityId();
        String role = userTypeInfo.getUserType();

        if(role.equals(Role.TEACHER.name()) && entityId == null) {
            throw new UnauthorizedException("Invalid Teacher");
        }
        return ApiResponse.ok(service.markBulk(dtos, entityId, userId));
    }

    // 4. Update attendance
    @PutMapping("/{attendanceId}")
    public ApiResponse<AttendanceResponseDto> update(
            @PathVariable Long attendanceId,
            @RequestBody AttendanceUpdateDto dto, HttpServletRequest request)   {

        UserTypeInfo userTypeInfo = requestContextService.getCurrentUserContext(request);

        Long userId = userTypeInfo.getUserId();
        Long entityId = userTypeInfo.getEntityId();
        String role = userTypeInfo.getUserType();

        if(role.equals(Role.TEACHER.name()) && entityId == null) {
            throw new UnauthorizedException("Invalid Teacher");
        }
        return ApiResponse.ok(service.update(attendanceId, dto, entityId, userId));
    }

    // 4. Delete a attendance record
    @DeleteMapping("/{attendanceId}")
    public ApiResponse<Void> delete(@PathVariable Long attendanceId, HttpServletRequest request) {
        UserTypeInfo userTypeInfo = requestContextService.getCurrentUserContext(request);

        Long userId = userTypeInfo.getUserId();
        Long entityId = userTypeInfo.getEntityId();
        String role = userTypeInfo.getUserType();

        if(role.equals(Role.TEACHER.name()) && entityId == null) {
            throw new UnauthorizedException("Invalid Teacher");
        }
        service.delete(attendanceId, entityId, userId, role);
        return ApiResponse.ok(null);
    }

    // 5. Get attendance by student (already implemented)
    @GetMapping("/students/{studentId}")
    public ApiResponse<List<AttendanceResponseDto>> byStudent(
            @PathVariable Long studentId,
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size, Sort.by("date").descending());
        Page<AttendanceResponseDto> pagedResult = service.byStudentId(studentId, pageable);
        return ApiResponse.paged(pagedResult);
    }

    // 6. Get attendance by class
//    @GetMapping("/classes/{classId}")
//    public ApiResponse<List<AttendanceResponseDto>> byClass(
//            @PathVariable Long classId,
//            @RequestParam(required = false, defaultValue = "0") int page,
//            @RequestParam(required = false, defaultValue = "10") int size) {
//        Pageable pageable = PageRequest.of(page, size, Sort.by("date").descending());
//        Page<AttendanceResponseDto> pagedResult = service.byClassId(classId, pageable);
//        return ApiResponse.paged(pagedResult);
//    }

    // 7. Get attendance by date
    @GetMapping("/dates/{date}")
    public ApiResponse<List<AttendanceResponseDto>> byDate(
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("date").descending());
        Page<AttendanceResponseDto> pagedResult = service.byDate(date, pageable);
        return ApiResponse.paged(pagedResult);
    }

    // 8. Get attendance by date range
    @GetMapping("/dates")
    public ApiResponse<List<AttendanceResponseDto>> byDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("date").descending());
        Page<AttendanceResponseDto> pagedResult = service.byDateRange(startDate, endDate, pageable);
        return ApiResponse.paged(pagedResult);
    }

    // 10. Get attendance by class and date
    @GetMapping("/classes/{classId}/dates/{date}")
    public ApiResponse<List<AttendanceResponseDto>> byClassAndDate(
            @PathVariable Long classId,
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        List<AttendanceResponseDto> result = service.byClassAndDate(classId, date);
        return ApiResponse.ok(result);
    }

//    // 10. Get attendance summary for a student
//    @GetMapping("/student/{id}/summary")
//    public ApiResponse<AttendanceSummaryDto> studentSummary(
//            @PathVariable Long id,
//            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
//            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
//
//    }
//
//    // 11. Get attendance summary for a class
//    @GetMapping("/class/{classId}/summary")
//    public ApiResponse<AttendanceSummaryDto> classSummary(
//            @PathVariable Long classId,
//            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
//            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate){
//
//    }
//
//    // 12. Get low attendance students
//    @GetMapping("/low-attendance")
//    public ApiResponse<List<StudentAttendanceDto>> lowAttendanceStudents(
//            @RequestParam(required = false, defaultValue = "75") double threshold,
//            @RequestParam(required = false, defaultValue = "0") int page,
//            @RequestParam(required = false, defaultValue = "10") int size){
//
//    }

    // 13. Get attendance percentage by student
    @GetMapping("/students/{studentId}/percentage")
    public ApiResponse<AttendancePercentageDto> studentAttendancePercentage(
            @PathVariable Long studentId,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate){
        return ApiResponse.ok(service.getAttendancePercentageByStudent(studentId, startDate, endDate));
    }

    /**
     * 14. Search attendance records
     * This endpoint allows searching attendance records based on various criteria.
     * It supports pagination and sorting by date in descending order.
     */
    @GetMapping("/search")
    public ApiResponse<List<AttendanceResponseDto>> search(
            @RequestParam(required = false) Long studentId,
            @RequestParam(required = false) Long sectionId,
            @RequestParam(required = false) Long classId,
            @RequestParam(required = false) AttendanceStatus status, // PRESENT, ABSENT, LATE
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            @RequestParam(required = false, defaultValue = "0") @Min(0) int page,
            @RequestParam(required = false, defaultValue = "10") @Min(1) @Max(100) int size){
        Pageable pageable = PageRequest.of(page, size, Sort.by("date").descending());
        Page<AttendanceResponseDto> pagedResult = service.search(studentId, sectionId, classId, status, startDate, endDate, pageable);
        return ApiResponse.paged(pagedResult);
    }

    /**
     * 15. Get today's attendance by section ID
     * This endpoint retrieves today's attendance records for a specific section.
     * If no section ID is provided, it returns all today's attendance records.
     */
    @GetMapping("/today")
    public ApiResponse<List<AttendanceResponseDto>> todayAttendanceBySectionId(
            @RequestParam(required = false) Long sectionId,
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "10") int size){
        Pageable pageable = PageRequest.of(page, size, Sort.by("date").descending());
        Page<AttendanceResponseDto> pagedResult = service.bySectionId(sectionId, pageable);
        return ApiResponse.paged(pagedResult);
    }

    // 16. Get monthly attendance report
    @GetMapping("/reports/monthly")
    public ApiResponse<MonthlyAttendanceReportDto> monthlyReport(
            @RequestParam int year,
            @RequestParam int month,
            @RequestParam(required = false) Long classId,
            @RequestParam(required = false) Long studentId){
        return ApiResponse.ok(service.getMonthlyReport(year, month, classId, studentId));
    }

    // 17. Get weekly attendance report
    @GetMapping("/reports/weekly")
    public ApiResponse<WeeklyAttendanceReportDto> weeklyReport(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate weekStartDate,
            @RequestParam(required = false) Long classId,
            @RequestParam(required = false) Long studentId){
        return ApiResponse.ok(service.getWeeklyReport(weekStartDate, classId, studentId));
    }

    // 18. Mark all present for a class
    @PostMapping("/classes/{classId}/mark-all-present")
    public ApiResponse<List<AttendanceResponseDto>> markAllPresent(
            @PathVariable Long classId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            @RequestParam @Valid Long teacherId, HttpServletRequest request){
        List<AttendanceResponseDto> result = service.markAllPresent(classId, date, teacherId);
        return ApiResponse.ok(result);
    }

    // 19. Import attendance from file
//    @PostMapping("/import")
//    public ApiResponse<ImportResultDto> importAttendance(
//            @RequestParam("file") MultipartFile file){
//        ImportResultDto result = service.importAttendance(file);
//        return ApiResponse.ok(result);
//    }
//
//    // 20. Export attendance to Excel
//    @GetMapping("/export")
//    public ResponseEntity<byte[]> exportAttendance(
//            @RequestParam(required = false) Long classId,
//            @RequestParam(required = false) Long studentId,
//            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
//            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate){
//        byte[] excelData = service.exportAttendanceToExcel(classId, studentId, startDate, endDate);
//        return ResponseEntity.ok()
//                .header("Content-Disposition", "attachment; filename=attendance_report.xlsx")
//                .body(excelData);
//    }
}