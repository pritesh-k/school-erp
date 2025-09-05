package com.schoolerp.controller;

import com.schoolerp.dto.request.FeeStructureItemRequest;
import com.schoolerp.dto.request.FeeStructureItemUpdateRequest;
import com.schoolerp.dto.request.FeeStructureRequest;
import com.schoolerp.dto.response.ApiResponse;
import com.schoolerp.dto.response.FeeStructureItemResponse;
import com.schoolerp.dto.response.FeeStructureResponse;
import com.schoolerp.entity.FeeStructureItem;
import com.schoolerp.entity.UserTypeInfo;
import com.schoolerp.service.RequestContextService;
import com.schoolerp.service.impl.FeeStructureItemServiceImpl;
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
@RequestMapping("/api/v1/feesStructureItem")
@Validated
@Slf4j
public class FeeStructureItemController {
    @Autowired
    private RequestContextService requestContextService;
    @Autowired
    private FeeStructureItemServiceImpl feeStructureItemService;

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/{id}")
    public ApiResponse<FeeStructureItemResponse> createFeeStructureItem( @PathVariable Long id,
            @Valid @RequestBody FeeStructureItemRequest request) {
        UserTypeInfo userTypeInfo = requestContextService.getCurrentUserContext();
        Long createdBy = userTypeInfo.getUserId();
        return ApiResponse.ok(feeStructureItemService.create(id, request, createdBy));
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/{id}")
    public ApiResponse<FeeStructureItemResponse> updateFeeStructureItem( @PathVariable Long id,
                                                                         @Valid @RequestBody FeeStructureItemUpdateRequest request) {
        UserTypeInfo userTypeInfo = requestContextService.getCurrentUserContext();
        Long updateBy = userTypeInfo.getUserId();
        return ApiResponse.ok(feeStructureItemService.update(id, request, updateBy));
    }
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('ACCOUNTANT')")
    @GetMapping
    public ApiResponse<List<FeeStructureItemResponse>> getAllFeeStructuresItem(
            @RequestParam(required = false, defaultValue = "0") @Min(0) Integer page,
            @RequestParam(required = false, defaultValue = "10") @Min(1) @Max(100) Integer size,
            @RequestParam(required = false) Long feeStructureId) {

        Pageable pageable = PageRequest.of(page, size);
        Page<FeeStructureItemResponse> results = feeStructureItemService.list(pageable, feeStructureId);
        return ApiResponse.paged(results);
    }

    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('ACCOUNTANT')")
    @GetMapping("/{id}")
    public ApiResponse<FeeStructureItemResponse> getFeeStructureItem(@PathVariable Long id) {
        return ApiResponse.ok(feeStructureItemService.getById(id));
    }
}
