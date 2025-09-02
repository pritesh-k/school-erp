//package com.schoolerp.controller;
//
//import com.schoolerp.dto.response.ApiResponse;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.format.annotation.DateTimeFormat;
//import org.springframework.security.access.prepost.PreAuthorize;
//import org.springframework.validation.annotation.Validated;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.time.LocalDate;
//
//@RestController
//@RequestMapping("/api/fees/reports")
//@Validated
//@Slf4j
//public class FeeReportController {
//
//    @Autowired
//    private FeeReportService reportService;
//
//    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('ACCOUNTANT')")
//    @GetMapping("/collection")
//    public ApiResponse<FeeCollectionReportResponse> getFeeCollectionReport(
//            @RequestParam Long sessionId,
//            @RequestParam(required = false) Long classId,
//            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
//            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
//        log.info("Generating collection report for session: {}, class: {}, period: {} to {}",
//                sessionId, classId, startDate, endDate);
//        return ApiResponse.ok(reportService.getFeeCollectionReport(sessionId, classId, startDate, endDate));
//    }
//
//    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('ACCOUNTANT')")
//    @GetMapping("/defaulters")
//    public ApiResponse<List<FeeDefaulterResponse>> getDefaultersReport(
//            @RequestParam Long sessionId,
//            @RequestParam(required = false) Long classId,
//            @RequestParam(required = false, defaultValue = "7") Integer daysOverdue) {
//        log.info("Generating defaulters report for session: {}, class: {}, days overdue: {}",
//                sessionId, classId, daysOverdue);
//        return ApiResponse.ok(reportService.getDefaultersReport(sessionId, classId, daysOverdue));
//    }
//
//    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('ACCOUNTANT')")
//    @GetMapping("/feehead-collection")
//    public ApiResponse<List<FeeHeadCollectionResponse>> getFeeHeadWiseCollection(
//            @RequestParam Long sessionId,
//            @RequestParam(required = false) Long feeHeadId,
//            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
//            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
//        return ApiResponse.ok(reportService.getFeeHeadWiseCollection(sessionId, feeHeadId, startDate, endDate));
//    }
//
//    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('ACCOUNTANT')")
//    @GetMapping("/class-summary")
//    public ApiResponse<List<ClassFeeSummaryResponse>> getClassWiseFeeSummary(@RequestParam Long sessionId) {
//        return ApiResponse.ok(reportService.getClassWiseFeeSummary(sessionId));
//    }
//}
