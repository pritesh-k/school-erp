//package com.schoolerp.controller;
//
//import com.schoolerp.dto.request.RecordPaymentRequest;
//import com.schoolerp.dto.response.ApiResponse;
//import com.schoolerp.dto.response.FeePaymentResponse;
//import com.schoolerp.dto.response.StudentFeeSummaryResponse;
//import com.schoolerp.entity.UserTypeInfo;
//import com.schoolerp.service.RequestContextService;
//import com.schoolerp.service.impl.FeeService;
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
//public class FeePaymentController {
//
//    @Autowired
//    private FeeService feeService;
//
//    @Autowired
//    private RequestContextService requestContextService;
//
//    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('ACCOUNTANT')")
//    @PostMapping("/payment")
//    public ApiResponse<FeePaymentResponse> recordPayment(@Valid @RequestBody RecordPaymentRequest request) {
//        log.info("Recording payment for assignment: {}, amount: {}",
//                request.getStudentFeeAssignmentId(), request.getAmount());
//        UserTypeInfo userTypeInfo = requestContextService.getCurrentUserContext();
//        Long receivedBy = userTypeInfo.getUserId();
//        return ApiResponse.ok(feeService.recordPayment(request, receivedBy));
//    }
//
//    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('ACCOUNTANT') or hasAuthority('TEACHER')")
//    @GetMapping("/students/{studentId}/summary")
//    public ApiResponse<StudentFeeSummaryResponse> getStudentFeeSummary(
//            @PathVariable Long studentId,
//            @RequestParam Long sessionId) {
//        log.info("Fetching fee summary for student: {}, session: {}", studentId, sessionId);
//        return ApiResponse.ok(feeService.getStudentFeeSummary(studentId, sessionId));
//    }
//
//    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('ACCOUNTANT') or hasAuthority('TEACHER')")
//    @GetMapping("/students/{studentId}/payments")
//    public ApiResponse<List<FeePaymentResponse>> getStudentPayments(
//            @PathVariable Long studentId,
//            @RequestParam(required = false, defaultValue = "0") @Min(0) Integer page,
//            @RequestParam(required = false, defaultValue = "20") @Min(1) @Max(100) Integer size,
//            @RequestParam(required = false) Long sessionId) {
//        log.info("Fetching payments for student: {}, page: {}, size: {}", studentId, page, size);
//        Pageable pageable = PageRequest.of(page, size);
//        Page<FeePaymentResponse> results = feeService.getPaymentHistory(studentId, pageable, sessionId);
//        return ApiResponse.paged(results);
//    }
//
//    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('ACCOUNTANT')")
//    @GetMapping("/payment/{id}")
//    public ApiResponse<FeePaymentResponse> getPayment(@PathVariable Long id) {
//        log.info("Fetching payment with id: {}", id);
//        return ApiResponse.ok(feeService.getPayment(id));
//    }
//
//    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('ACCOUNTANT')")
//    @PutMapping("/payment/{id}")
//    public ApiResponse<FeePaymentResponse> updatePayment(
//            @PathVariable Long id,
//            @Valid @RequestBody UpdatePaymentRequest request) {
//        log.info("Updating payment with id: {}", id);
//        UserTypeInfo userTypeInfo = requestContextService.getCurrentUserContext();
//        Long updatedBy = userTypeInfo.getUserId();
//        return ApiResponse.ok(feeService.updatePayment(id, request, updatedBy));
//    }
//
//    @PreAuthorize("hasAuthority('ADMIN')")
//    @PutMapping("/payment/{id}/cancel")
//    public ApiResponse<FeePaymentResponse> cancelPayment(
//            @PathVariable Long id,
//            @Valid @RequestBody CancelPaymentRequest request) {
//        log.info("Cancelling payment with id: {}, reason: {}", id, request.getReason());
//        UserTypeInfo userTypeInfo = requestContextService.getCurrentUserContext();
//        Long cancelledBy = userTypeInfo.getUserId();
//        return ApiResponse.ok(feeService.cancelPayment(id, request.getReason(), cancelledBy));
//    }
//
//    @PreAuthorize("hasAuthority('ADMIN')")
//    @PostMapping("/payment/{id}/refund")
//    public ApiResponse<FeePaymentResponse> refundPayment(
//            @PathVariable Long id,
//            @Valid @RequestBody RefundPaymentRequest request) {
//        log.info("Processing refund for payment id: {}, amount: {}", id, request.getAmount());
//        UserTypeInfo userTypeInfo = requestContextService.getCurrentUserContext();
//        Long processedBy = userTypeInfo.getUserId();
//        return ApiResponse.ok(feeService.refundPayment(id, request.getAmount(), request.getReason(), processedBy));
//    }
//
//    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('ACCOUNTANT')")
//    @PostMapping("/payments/bulk")
//    public ApiResponse<List<FeePaymentResponse>> bulkRecordPayments(
//            @Valid @RequestBody BulkPaymentRequest request) {
//        log.info("Processing bulk payments: {} records", request.getPayments().size());
//        UserTypeInfo userTypeInfo = requestContextService.getCurrentUserContext();
//        Long receivedBy = userTypeInfo.getUserId();
//        return ApiResponse.ok(feeService.bulkRecordPayments(request.getPayments(), receivedBy));
//    }
//}
