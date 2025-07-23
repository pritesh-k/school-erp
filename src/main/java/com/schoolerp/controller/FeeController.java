package com.schoolerp.controller;

import com.schoolerp.dto.request.FeeAssignDto;
import com.schoolerp.dto.request.FeePaymentDto;
import com.schoolerp.dto.request.FeeStructureDto;
import com.schoolerp.dto.response.ApiResponse;
import com.schoolerp.entity.*;
import com.schoolerp.service.RequestContextService;
import com.schoolerp.service.impl.FeeHeadService;
import com.schoolerp.service.impl.FeeRecordService;
import com.schoolerp.service.impl.FeeStructureService;
import com.schoolerp.service.impl.StudentFeeAssignmentService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/fees")
@RequiredArgsConstructor
public class FeeController {
    private final FeeHeadService feeHeadService;
    private final FeeStructureService feeStructureService;
    private final StudentFeeAssignmentService assignmentService;
    private final FeeRecordService feeRecordService;
    private final RequestContextService requestContextService;

    // FeeHead CRUD
    @PostMapping("/head")
    public ApiResponse<FeeHead> createFeeHead(@RequestBody FeeHead feeHead) {
        return ApiResponse.ok(feeHeadService.create(feeHead));
    }
    @GetMapping("/head/{id}")
    public ApiResponse<FeeHead> getFeeHead(@PathVariable Long id) {
        return ApiResponse.ok(feeHeadService.get(id));
    }

    // FeeStructure CRUD
    @PostMapping("/structure")
    public ApiResponse<FeeStructure> createStructure(@RequestBody FeeStructureDto dto, HttpServletRequest request) {
        UserTypeInfo userTypeInfo = requestContextService.getCurrentUserContext(request);
        return ApiResponse.ok(feeStructureService.create(dto));
    }
    @GetMapping("/structure/{id}")
    public ApiResponse<FeeStructure> getStructure(@PathVariable Long id) {
        return ApiResponse.ok(feeStructureService.get(id));
    }

    // Assign fee to student
    @PostMapping("/assign")
    public ApiResponse<StudentFeeAssignment> assignFee(@RequestBody FeeAssignDto dto, HttpServletRequest request) {
        UserTypeInfo userTypeInfo = requestContextService.getCurrentUserContext(request);
        return ApiResponse.ok(assignmentService.assign(dto));
    }

    // Record a payment
    @PostMapping("/pay")
    public ApiResponse<FeeRecord> payFee(@RequestBody FeePaymentDto dto, HttpServletRequest request) {
        UserTypeInfo userTypeInfo = requestContextService.getCurrentUserContext(request);
        return ApiResponse.ok(feeRecordService.record(dto));
    }

    // List assignments/payments for student
    @GetMapping("/student/{studentId}/assignments")
    public ApiResponse<List<StudentFeeAssignment>> listAssignments(@PathVariable Long studentId) {
        return ApiResponse.ok(assignmentService.listByStudent(studentId));
    }
    @GetMapping("/assignment/{id}/payments")
    public ApiResponse<List<FeeRecord>> listPayments(@PathVariable Long id) {
        return ApiResponse.ok(feeRecordService.listByAssignment(id));
    }
}
