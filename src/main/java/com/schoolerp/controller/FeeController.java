package com.schoolerp.controller;

import com.schoolerp.dto.request.FeeAssignDto;
import com.schoolerp.dto.request.FeePaymentDto;
import com.schoolerp.dto.request.FeeStructureDto;
import com.schoolerp.dto.request.feeHead.FeeHeadRequest;
import com.schoolerp.dto.response.ApiResponse;
import com.schoolerp.entity.*;
import com.schoolerp.service.RequestContextService;
import com.schoolerp.service.impl.FeeHeadService;
import com.schoolerp.service.impl.FeeRecordServiceImpl;
import com.schoolerp.service.impl.FeeStructureService;
import com.schoolerp.service.impl.StudentFeeAssignmentService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/fees")
@RequiredArgsConstructor
public class FeeController {
    private final FeeHeadService feeHeadService;
    private final FeeStructureService feeStructureService;
    private final StudentFeeAssignmentService assignmentService;
    private final FeeRecordServiceImpl feeRecordServiceImpl;

    @Autowired
    private final RequestContextService requestContextService;

    // FeeHead CRUD
    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/head")
    public ApiResponse<FeeHeadRequest> createFeeHead(@RequestBody FeeHeadRequest head) {
        return ApiResponse.ok(feeHeadService.create(head));
    }

    @GetMapping("/head/{feeHeadId}")
    public ApiResponse<FeeHead> getFeeHead(@PathVariable Long feeHeadId) {
        return ApiResponse.ok(feeHeadService.get(feeHeadId));
    }

    @GetMapping("/head")
    public ApiResponse<List<FeeHeadRequest>> getAllFeeHead(
            @RequestParam(required = false, defaultValue = "0") @Min(0) Integer page,
            @RequestParam(required = false, defaultValue = "10") @Min(1) @Max(100) Integer size) {
        UserTypeInfo userTypeInfo = requestContextService.getCurrentUserContext();
        Pageable pageable = PageRequest.of(page, size);
        return ApiResponse.paged(feeHeadService.list(pageable));
    }

    // FeeStructure CRUD
    @PostMapping("/structure")
    public ApiResponse<FeeStructure> createStructure(@RequestBody FeeStructureDto dto, HttpServletRequest request) {
        UserTypeInfo userTypeInfo = requestContextService.getCurrentUserContext();
        return ApiResponse.ok(feeStructureService.create(dto));
    }
    @GetMapping("/structure/{id}")
    public ApiResponse<FeeStructure> getStructure(@PathVariable Long id) {
        return ApiResponse.ok(feeStructureService.get(id));
    }

    // Assign fee to student
    @PostMapping("/assign")
    public ApiResponse<StudentFeeAssignment> assignFee(@RequestBody FeeAssignDto dto, HttpServletRequest request) {
        UserTypeInfo userTypeInfo = requestContextService.getCurrentUserContext();
        return ApiResponse.ok(assignmentService.assign(dto));
    }

    // Record a payment
    @PostMapping("/pay")
    public ApiResponse<FeeRecord> payFee(@RequestBody FeePaymentDto dto, HttpServletRequest request) {
        UserTypeInfo userTypeInfo = requestContextService.getCurrentUserContext();
        return ApiResponse.ok(feeRecordServiceImpl.record(dto));
    }

    // List assignments/payments for student
    @GetMapping("/student/{studentId}/assignments")
    public ApiResponse<List<StudentFeeAssignment>> listAssignments(@PathVariable Long studentId) {
        return ApiResponse.ok(assignmentService.listByStudent(studentId));
    }
    @GetMapping("/assignment/{id}/payments")
    public ApiResponse<List<FeeRecord>> listPayments(@PathVariable Long id) {
        return ApiResponse.ok(feeRecordServiceImpl.listByAssignment(id));
    }
}
