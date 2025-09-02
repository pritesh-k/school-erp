package com.schoolerp.controller;

import com.schoolerp.dto.response.ApiResponse;
import com.schoolerp.dto.response.AttendanceSummaryDto;
import com.schoolerp.dto.response.ClassAttendanceSummaryDto;
import com.schoolerp.service.impl.AttendanceSummaryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/attendance/summary")
@RequiredArgsConstructor
public class AttendanceSummaryController {

    private final AttendanceSummaryService service;

    @GetMapping("/today")
    public ApiResponse<AttendanceSummaryDto> getTodaySummary() {
        return ApiResponse.ok(service.getTodaySummary());
    }

    @GetMapping("/today/classwise")
    public ApiResponse<List<ClassAttendanceSummaryDto>> getTodayClassWiseSummary() {
        return ApiResponse.ok(service.getTodayClassWiseSummary());
    }
}

