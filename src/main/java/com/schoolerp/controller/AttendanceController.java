package com.schoolerp.controller;

import com.schoolerp.dto.request.AttendanceCreateDto;
import com.schoolerp.dto.response.ApiResponse;
import com.schoolerp.dto.response.AttendanceResponseDto;
import com.schoolerp.dto.response.StudentResponseDto;
import com.schoolerp.dto.response.attendance.*;
import com.schoolerp.enums.AttendanceStatus;
import com.schoolerp.service.AttendanceService;
import jakarta.validation.Valid;
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

    // 1. Mark today's attendance for a student
    @PostMapping("/students/{studentId}/today")
    public ApiResponse<AttendanceResponseDto> attendanceToday(
            @PathVariable @NotNull Long studentId,
            @RequestParam AttendanceStatus attendanceStatus,
            @RequestParam(required = false) String remarks,
            @RequestParam(required = true) Long teacherId) {

        return ApiResponse.ok(service.markTodayAttendance(studentId, attendanceStatus, remarks, teacherId));
    }


    // 2. Mark attendance for a student
    @PostMapping("/students/{studentId}")
    public ApiResponse<AttendanceResponseDto> attendance(@PathVariable @NotNull Long studentId, @RequestBody AttendanceCreateDto dto) {
        return ApiResponse.ok(service.mark(dto));
    }

    // 3. Mark bulk attendance
    @PostMapping("/bulk")
    public ApiResponse<List<AttendanceResponseDto>> markBulk(@RequestBody List<AttendanceCreateDto> dtos) {
        return ApiResponse.ok(service.markBulk(dtos));
    }

    // 4. Update attendance
    @PutMapping("/{attendanceId}")
    public ApiResponse<AttendanceResponseDto> update(
            @PathVariable Long attendanceId,
            @RequestBody AttendanceCreateDto dto)   {
        return ApiResponse.ok(service.update(attendanceId, dto));
    }

    // 4. Delete a attendance record
    @DeleteMapping("/{attendanceId}")
    public ApiResponse<Void> delete(@PathVariable Long attendanceId) {
        service.delete(attendanceId);
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
    @GetMapping("/classes/{classId}")
    public ApiResponse<List<AttendanceResponseDto>> byClass(
            @PathVariable Long classId,
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("date").descending());
        Page<AttendanceResponseDto> pagedResult = service.byClassId(classId, pageable);
        return ApiResponse.paged(pagedResult);
    }

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

    // 14. Search attendance with filters
    @GetMapping("/search")
    public ApiResponse<List<AttendanceResponseDto>> search(
            @RequestParam(required = false) Long studentId,
            @RequestParam(required = false) Long sectionId,
            @RequestParam(required = false) Long classId,
            @RequestParam(required = false) AttendanceStatus status, // PRESENT, ABSENT, LATE
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "10") int size){
        Pageable pageable = PageRequest.of(page, size, Sort.by("date").descending());
        Page<AttendanceResponseDto> pagedResult = service.search(studentId, sectionId, classId, status, startDate, endDate, pageable);
        return ApiResponse.paged(pagedResult);
    }

    // 15. Get today's attendance, If classId is provided, return attendance for that class else return all classes
    @GetMapping("/today")
    public ApiResponse<List<AttendanceResponseDto>> todayAttendance(
            @RequestParam(required = false) Long classId,
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "10") int size){
        Pageable pageable = PageRequest.of(page, size, Sort.by("date").descending());
        Page<AttendanceResponseDto> pagedResult = service.getTodayAttendance(classId, pageable);
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
            @RequestParam @Valid Long teacherId){
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