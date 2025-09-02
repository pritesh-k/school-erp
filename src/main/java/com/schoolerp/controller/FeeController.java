//package com.schoolerp.controller;
//
//import com.schoolerp.dto.request.FeeAssignDto;
//import com.schoolerp.dto.request.FeeHeadRequest;
//import com.schoolerp.dto.request.FeeStructureDto;
//import com.schoolerp.dto.response.ApiResponse;
//import com.schoolerp.dto.response.FeeHeadResponse;
//import com.schoolerp.dto.response.FeeStructureResponse;
//import com.schoolerp.entity.FeeHead;
//import com.schoolerp.entity.FeeStructure;
//import com.schoolerp.entity.StudentFeeAssignment;
//import com.schoolerp.entity.UserTypeInfo;
//import com.schoolerp.service.RequestContextService;
//import com.schoolerp.service.impl.FeeHeadService;
//import com.schoolerp.service.impl.FeeStructureService;
//import com.schoolerp.service.impl.StudentFeeAssignmentService;
//import jakarta.validation.constraints.Max;
//import jakarta.validation.constraints.Min;
//import lombok.RequiredArgsConstructor;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.data.domain.Pageable;
//import org.springframework.security.access.prepost.PreAuthorize;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/api/v1/fees")
//@RequiredArgsConstructor
//public class FeeController {
//    private final FeeHeadService feeHeadService;
//    private final FeeStructureService feeStructureService;
//    private final StudentFeeAssignmentService assignmentService;
//
//    @Autowired
//    private final RequestContextService requestContextService;
//
//    // FeeHead CRUD
//    @PreAuthorize("hasAuthority('ADMIN')")
//    @PostMapping("/head")
//    public ApiResponse<FeeHeadResponse> createFeeHead(@RequestBody FeeHeadRequest head) {
//        UserTypeInfo userTypeInfo = requestContextService.getCurrentUserContext();
//        Long createdBy = userTypeInfo.getUserId();
//        return ApiResponse.ok(feeHeadService.create(head, createdBy));
//    }
//
//    @PreAuthorize("hasAuthority('ADMIN')")
//    @GetMapping("/head/{feeHeadId}")
//    public ApiResponse<FeeHeadResponse> getFeeHead(@PathVariable Long feeHeadId) {
//        UserTypeInfo userTypeInfo = requestContextService.getCurrentUserContext();
//        return ApiResponse.ok(feeHeadService.getById(feeHeadId));
//    }
//
//    @PreAuthorize("hasAuthority('ADMIN')")
//    @GetMapping("/head")
//    public ApiResponse<List<FeeHeadResponse>> getAllFeeHead(
//            @RequestParam(required = false, defaultValue = "0") @Min(0) Integer page,
//            @RequestParam(required = false, defaultValue = "10") @Min(1) @Max(100) Integer size) {
//        UserTypeInfo userTypeInfo = requestContextService.getCurrentUserContext();
//        Pageable pageable = PageRequest.of(page, size);
//        Page<FeeHeadResponse> results = feeHeadService.list(pageable);
//        return ApiResponse.paged(results);
//    }
//
//    // FeeStructure CRUD
//    @PostMapping("/structure")
//    public ApiResponse<FeeStructureResponse> createStructure(@RequestBody FeeStructureDto dto) {
//        UserTypeInfo userTypeInfo = requestContextService.getCurrentUserContext();
//        Long createdBy = userTypeInfo.getUserId();
//        return ApiResponse.ok(feeStructureService.create(dto, createdBy));
//    }
//    @GetMapping("/structure/{id}")
//    public ApiResponse<FeeStructureResponse> getStructure(@PathVariable Long id) {
//        return ApiResponse.ok(feeStructureService.getById(id));
//    }
//
//    // Assign fee to student
//    @PostMapping("/assign")
//    public ApiResponse<StudentFeeAssignment> assignFee(@RequestBody FeeAssignDto dto) {
//        UserTypeInfo userTypeInfo = requestContextService.getCurrentUserContext();
//        return ApiResponse.ok(assignmentService.assign(dto));
//    }
//    // List assignments/payments for student
//    @GetMapping("/student/{studentId}/assignments")
//    public ApiResponse<List<StudentFeeAssignment>> listAssignments(
//            @PathVariable Long studentId,
//            @RequestParam(required = false, defaultValue = "0") @Min(0) Integer page,
//            @RequestParam(required = false, defaultValue = "10") @Min(1) @Max(100) Integer size) {
//        UserTypeInfo userTypeInfo = requestContextService.getCurrentUserContext();
//        Pageable pageable = PageRequest.of(page, size);
//        return ApiResponse.paged(assignmentService.listByStudent(studentId, pageable));
//    }
//}
