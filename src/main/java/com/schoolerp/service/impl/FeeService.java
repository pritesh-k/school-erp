package com.schoolerp.service.impl;

import com.schoolerp.dto.request.RecordPaymentRequest;
import com.schoolerp.dto.request.UpdatePaymentRequest;
import com.schoolerp.dto.response.FeePaymentResponse;
import com.schoolerp.entity.FeeHead;
import com.schoolerp.entity.FeePayment;
import com.schoolerp.entity.StudentFeeAssignment;
import com.schoolerp.enums.PaymentMode;
import com.schoolerp.enums.PaymentStatus;
import com.schoolerp.exception.ResourceNotFoundException;
import com.schoolerp.mapper.FeePaymentMapper;
import com.schoolerp.repository.FeeHeadRepository;
import com.schoolerp.repository.FeePaymentRepository;
import com.schoolerp.repository.StudentFeeAssignmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class FeeService {

    private final FeePaymentRepository feePaymentRepo;
    private final StudentFeeAssignmentRepository assignmentRepo;
    private final FeeHeadRepository feeHeadRepo;
    private final FeePaymentMapper feePaymentMapper;

    /**
     * Record a single payment for a student fee assignment
     */
    public FeePaymentResponse recordPayment(RecordPaymentRequest request, Long receivedBy) {
        StudentFeeAssignment assignment = assignmentRepo.findById(request.getStudentFeeAssignmentId())
                .orElseThrow(() -> new ResourceNotFoundException("Student fee assignment not found: " + request.getStudentFeeAssignmentId()));

        FeeHead feeHead = feeHeadRepo.findById(request.getFeeHeadId())
                .orElseThrow(() -> new ResourceNotFoundException("Fee head not found: " + request.getFeeHeadId()));

        FeePayment payment = FeePayment.builder()
                .studentFeeAssignment(assignment)
                .feeHead(feeHead)
                .amount(request.getAmount())
                .paymentDate(LocalDateTime.now())
                .paymentMode(PaymentMode.valueOf(request.getPaymentMode().toUpperCase()))
                .status(PaymentStatus.PAID) // default to PAID when recorded
                .transactionReference(request.getTransactionReference())
                .remarks(request.getRemarks())
                .receivedBy(String.valueOf(receivedBy))
                .build();

        payment = feePaymentRepo.save(payment);
        return feePaymentMapper.toResponse(payment);
    }

    /**
     * Fetch paged payment history for a student
     */
    @Transactional(readOnly = true)
    public Page<FeePaymentResponse> getPaymentHistory(Long studentId, Pageable pageable, Long sessionId) {
        Page<FeePayment> results;
        if (sessionId != null) {
            results = feePaymentRepo.findByStudentIdAndSessionId(studentId, sessionId, pageable);
        } else {
            results = feePaymentRepo.findByStudentId(studentId, pageable);
        }
        return results.map(feePaymentMapper::toResponse);
    }

    /**
     * Fetch single payment by ID
     */
    @Transactional(readOnly = true)
    public FeePaymentResponse getPayment(Long id) {
        FeePayment payment = feePaymentRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Payment not found: " + id));
        return feePaymentMapper.toResponse(payment);
    }

    /**
     * Update a payment record
     */
    public FeePaymentResponse updatePayment(Long id, UpdatePaymentRequest request, Long updatedBy) {
        FeePayment payment = feePaymentRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Payment not found: " + id));

        if (request.getAmount() != null) {
            payment.setAmount(request.getAmount());
        }
        if (request.getPaymentMode() != null) {
            payment.setPaymentMode(PaymentMode.valueOf(request.getPaymentMode().toUpperCase()));
        }
        if (request.getTransactionReference() != null) {
            payment.setTransactionReference(request.getTransactionReference());
        }
        if (request.getRemarks() != null) {
            payment.setRemarks(request.getRemarks());
        }

        payment.setUpdatedAt(Instant.now());
        payment.setUpdatedBy(updatedBy);

        payment = feePaymentRepo.save(payment);
        return feePaymentMapper.toResponse(payment);
    }

    /**
     * Bulk record multiple payments
     */
    public List<FeePaymentResponse> bulkRecordPayments(List<RecordPaymentRequest> requests, Long receivedBy) {
        List<FeePaymentResponse> responses = new ArrayList<>();
        for (RecordPaymentRequest request : requests) {
            responses.add(recordPayment(request, receivedBy));
        }
        return responses;
    }
}

