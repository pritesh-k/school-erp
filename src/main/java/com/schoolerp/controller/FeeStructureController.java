package com.schoolerp.controller;

import com.schoolerp.dto.request.FeeStructureMigration;
import com.schoolerp.dto.request.FeeStructureRequest;
import com.schoolerp.dto.request.FeeStructureUpdateRequest;
import com.schoolerp.dto.response.ApiResponse;
import com.schoolerp.dto.response.FeeStructureResponse;
import com.schoolerp.entity.UserTypeInfo;
import com.schoolerp.service.RequestContextService;
import com.schoolerp.service.impl.FeeStructureService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/fees")
@Validated
@Slf4j
public class FeeStructureController {

    @Autowired
    private FeeStructureService feeStructureService;

    @Autowired
    private RequestContextService requestContextService;

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/structure")
    public ApiResponse<FeeStructureResponse> createFeeStructure(@Valid @RequestBody FeeStructureRequest request) {
        log.info("Creating fee structure: {}", request.getName());
        UserTypeInfo userTypeInfo = requestContextService.getCurrentUserContext();
        Long createdBy = userTypeInfo.getUserId();
        String academicSession = userTypeInfo.getAcademicSession();
        return ApiResponse.ok(feeStructureService.create(request, createdBy, academicSession));
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/structure/migrateToNextSession")
    public ApiResponse<FeeStructureResponse> migrateToNextSession(@RequestBody FeeStructureMigration request ) {
        UserTypeInfo userTypeInfo = requestContextService.getCurrentUserContext();
        Long createdBy = userTypeInfo.getUserId();
        long feeStructureId = request.getFeeStructureId();
        long newSessionId = request.getAcademicSessionId();
        return ApiResponse.ok(feeStructureService.migrateToNextSession(feeStructureId, newSessionId, createdBy));
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/structure/{id}")
    public ApiResponse<FeeStructureResponse> updateFeeStructure(
            @Valid @RequestBody FeeStructureUpdateRequest request, @RequestParam Long id) {
        UserTypeInfo userTypeInfo = requestContextService.getCurrentUserContext();
        Long updatedBy = userTypeInfo.getUserId();
        return ApiResponse.ok(feeStructureService.update(request, updatedBy, id));
    }

    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('ACCOUNTANT')")
    @GetMapping("/structure")
    public ApiResponse<List<FeeStructureResponse>> getAllFeeStructures(
            @RequestParam(required = false, defaultValue = "0") @Min(0) Integer page,
            @RequestParam(required = false, defaultValue = "10") @Min(1) @Max(100) Integer size) {
        UserTypeInfo userTypeInfo = requestContextService.getCurrentUserContext();
        String sessionName = userTypeInfo.getAcademicSession();
        Pageable pageable = PageRequest.of(page, size);
        Page<FeeStructureResponse> results = feeStructureService.list(pageable, sessionName);
        return ApiResponse.paged(results);
    }

    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('ACCOUNTANT')")
    @GetMapping("/structure/{id}")
    public ApiResponse<FeeStructureResponse> getFeeStructure(@PathVariable Long id) {
        FeeStructureResponse response = feeStructureService.getById(id);
        return ApiResponse.ok(response);
    }

    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('ACCOUNTANT')")
    @GetMapping("/structure/byClass/{id}")
    public ApiResponse<FeeStructureResponse> getFeeStructureByClass(@PathVariable Long id) {
        FeeStructureResponse response = feeStructureService.getFeeStructureByClass(id);
        return ApiResponse.ok(response);
    }

}

