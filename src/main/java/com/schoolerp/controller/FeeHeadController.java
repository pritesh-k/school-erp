//package com.schoolerp.controller;
//
//import com.schoolerp.dto.request.FeeHeadRequest;
//import com.schoolerp.dto.request.FeeHeadUpdateRequest;
//import com.schoolerp.dto.response.ApiResponse;
//import com.schoolerp.dto.response.FeeHeadResponse;
//import com.schoolerp.entity.UserTypeInfo;
//import com.schoolerp.service.RequestContextService;
//import com.schoolerp.service.impl.FeeHeadService;
//import jakarta.validation.Valid;
//import jakarta.validation.constraints.Max;
//import jakarta.validation.constraints.Min;
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
//@RequestMapping("/api/v1/fees")
//@Validated
//public class FeeHeadController {
//
//    @Autowired
//    private FeeHeadService feeHeadService;
//    @Autowired
//    private RequestContextService requestContextService;
//
//    @PreAuthorize("hasAuthority('ADMIN')")
//    @PostMapping("/head")
//    public ApiResponse<FeeHeadResponse> createFeeHead(@Valid @RequestBody FeeHeadRequest request) {
//        UserTypeInfo userTypeInfo = requestContextService.getCurrentUserContext();
//        Long createdBy = userTypeInfo.getUserId();
//        return ApiResponse.ok(feeHeadService.create(request, createdBy));
//    }
//
//    @PreAuthorize("hasAuthority('ADMIN')")
//    @GetMapping("/head")
//    public ApiResponse<List<FeeHeadResponse>> getAllFeeHead(
//            @RequestParam(required = false, defaultValue = "0") @Min(0) Integer page,
//            @RequestParam(required = false, defaultValue = "10") @Min(1) @Max(100) Integer size) {
//        Pageable pageable = PageRequest.of(page, size);
//        Page<FeeHeadResponse> results = feeHeadService.list(pageable);
//        return ApiResponse.paged(results);
//    }
//
//    @PreAuthorize("hasAuthority('ADMIN')")
//    @GetMapping("/head/{id}")
//    public ApiResponse<FeeHeadResponse> getFeeHead(@PathVariable Long id) {
//        return ApiResponse.ok(feeHeadService.getById(id));
//    }
//
//    @PreAuthorize("hasAuthority('ADMIN')")
//    @PutMapping("/head/{id}")
//    public ApiResponse<FeeHeadResponse> updateFeeHead(
//            @PathVariable Long id,
//            @Valid @RequestBody FeeHeadUpdateRequest request) {
//        UserTypeInfo userTypeInfo = requestContextService.getCurrentUserContext();
//        Long updatedBy = userTypeInfo.getUserId();
//        return ApiResponse.ok(feeHeadService.update(id, request, updatedBy));
//    }
//
//    @PreAuthorize("hasAuthority('ADMIN')")
//    @DeleteMapping("/head/{id}")
//    public ApiResponse<Void> deleteFeeHead(@PathVariable Long id) {
//        feeHeadService.delete(id);
//        return ApiResponse.ok(null);
//    }
//}
