//package com.schoolerp.controller;
//
//import com.schoolerp.dto.request.FeeStructureRequest;
//import com.schoolerp.dto.response.ApiResponse;
//import com.schoolerp.dto.response.FeeStructureResponse;
//import com.schoolerp.entity.UserTypeInfo;
//import com.schoolerp.service.RequestContextService;
//import com.schoolerp.service.impl.FeeStructureService;
//import jakarta.validation.Valid;
//import jakarta.validation.constraints.Max;
//import jakarta.validation.constraints.Min;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.data.domain.Pageable;
//import org.springframework.security.access.prepost.PreAuthorize;
//import org.springframework.validation.annotation.Validated;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/api/fees")
//@Validated
//@Slf4j
//public class FeeStructureController {
//
//    @Autowired
//    private FeeStructureService feeStructureService;
//
//    @Autowired
//    private RequestContextService requestContextService;
//
//    @PreAuthorize("hasAuthority('ADMIN')")
//    @PostMapping("/structure")
//    public ApiResponse<FeeStructureResponse> createFeeStructure(@Valid @RequestBody FeeStructureRequest request) {
//        log.info("Creating fee structure: {}", request.getName());
//        UserTypeInfo userTypeInfo = requestContextService.getCurrentUserContext();
//        Long createdBy = userTypeInfo.getUserId();
//        String academicSession = userTypeInfo.getAcademicSession();
//        return ApiResponse.ok(feeStructureService.create(request, createdBy, academicSession));
//    }
//
//    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('ACCOUNTANT')")
//    @GetMapping("/structure")
//    public ApiResponse<List<FeeStructureResponse>> getAllFeeStructures(
//            @RequestParam(required = false, defaultValue = "0") @Min(0) Integer page,
//            @RequestParam(required = false, defaultValue = "10") @Min(1) @Max(100) Integer size,
//            @RequestParam(required = false) Long sessionId,
//            @RequestParam(required = false) Long classId) {
//        log.info("Fetching fee structures - page: {}, size: {}, sessionId: {}, classId: {}",
//                page, size, sessionId, classId);
//        Pageable pageable = PageRequest.of(page, size);
//        Page<FeeStructureResponse> results = feeStructureService.list(pageable, sessionId, classId);
//        return ApiResponse.paged(results);
//    }
//
//    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('ACCOUNTANT')")
//    @GetMapping("/structure/{id}")
//    public ApiResponse<FeeStructureResponse> getFeeStructure(@PathVariable Long id) {
//        return ApiResponse.ok(feeStructureService.getById(id));
//    }
//
//    @PreAuthorize("hasAuthority('ADMIN')")
//    @PutMapping("/structure/{id}")
//    public ApiResponse<FeeStructureResponse> updateFeeStructure(
//            @PathVariable Long id,
//            @Valid @RequestBody FeeStructureRequest request) {
//        UserTypeInfo userTypeInfo = requestContextService.getCurrentUserContext();
//        Long updatedBy = userTypeInfo.getUserId();
//        return ApiResponse.ok(feeStructureService.update(id, request, updatedBy));
//    }
//
//    @PreAuthorize("hasAuthority('ADMIN')")
//    @DeleteMapping("/structure/{id}")
//    public ApiResponse<Void> deleteFeeStructure(@PathVariable Long id) {
//        feeStructureService.delete(id);
//        return ApiResponse.ok(null);
//    }
//
//    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('ACCOUNTANT')")
//    @GetMapping("/structure/search")
//    public ApiResponse<List<FeeStructureResponse>> getFeeStructuresBySessionAndClass(
//            @RequestParam Long sessionId,
//            @RequestParam Long classId) {
//        return ApiResponse.ok(feeStructureService.findBySessionAndClass(sessionId, classId));
//    }
//
//    @PreAuthorize("hasAuthority('ADMIN')")
//    @PostMapping("/structure/{id}/clone")
//    public ApiResponse<FeeStructureResponse> cloneFeeStructure(
//            @PathVariable Long id,
//            @Valid @RequestBody CloneFeeStructureRequest request) {
//                id, request.getSessionId(), request.getClassId());
//        UserTypeInfo userTypeInfo = requestContextService.getCurrentUserContext();
//        Long createdBy = userTypeInfo.getUserId();
//        return ApiResponse.ok(feeStructureService.clone(id, request, createdBy));
//    }
//}
//
