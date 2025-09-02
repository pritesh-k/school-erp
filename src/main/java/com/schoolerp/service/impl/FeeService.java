//package com.schoolerp.service.impl;
//
//import com.schoolerp.dto.request.RecordPaymentRequest;
//import com.schoolerp.dto.response.FeeHeadBalanceResponse;
//import com.schoolerp.dto.response.FeePaymentResponse;
//import com.schoolerp.dto.response.StudentFeeSummaryResponse;
//import com.schoolerp.entity.FeeHead;
//import com.schoolerp.entity.FeePayment;
//import com.schoolerp.entity.FeeStructureItem;
//import com.schoolerp.entity.StudentFeeAssignment;
//import com.schoolerp.enums.PaymentMode;
//import com.schoolerp.enums.PaymentStatus;
//import com.schoolerp.mapper.FeeHeadMapper;
//import com.schoolerp.mapper.FeePaymentMapper;
//import com.schoolerp.repository.FeeHeadRepository;
//import com.schoolerp.repository.FeePaymentRepository;
//import com.schoolerp.repository.StudentFeeAssignmentRepository;
//import jakarta.persistence.EntityNotFoundException;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.Pageable;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.math.BigDecimal;
//import java.time.LocalDateTime;
//import java.util.List;
//import java.util.stream.Collectors;
//
//@Service
//@Transactional
//public class FeeService {
//
//    @Autowired
//    private StudentFeeAssignmentRepository assignmentRepository;
//
//    @Autowired
//    private FeePaymentRepository paymentRepository;
//
//    @Autowired
//    private FeeHeadRepository feeHeadRepository;
//
//    @Autowired
//    private ReceiptService receiptService;
//
//    @Autowired FeePaymentMapper feePaymentMapper;
//
//    public FeePaymentResponse recordPayment(RecordPaymentRequest request, Long receivedBy) {
//        StudentFeeAssignment assignment = assignmentRepository.findById(request.getStudentFeeAssignmentId())
//                .orElseThrow(() -> new EntityNotFoundException("Student fee assignment not found"));
//
//        FeeHead feeHead = feeHeadRepository.findById(request.getFeeHeadId())
//                .orElseThrow(() -> new EntityNotFoundException("Fee head not found"));
//
//        FeePayment payment = FeePayment.builder()
//                .studentFeeAssignment(assignment)
//                .feeHead(feeHead)
//                .amount(request.getAmount())
//                .paymentDate(LocalDateTime.now())
//                .paymentMode(PaymentMode.valueOf(request.getPaymentMode()))
//                .status(PaymentStatus.PAID)
//                .transactionReference(request.getTransactionReference())
//                .remarks(request.getRemarks())
//                .receivedBy(String.valueOf(receivedBy))
//                .build();
//
//        payment = paymentRepository.save(payment);
//
//        return feePaymentMapper.toResponse(payment);
//    }
//
//    @Transactional(readOnly = true)
//    public StudentFeeSummaryResponse getStudentFeeSummary(Long studentId, Long sessionId) {
//        StudentFeeAssignment assignment = assignmentRepository
//                .findByStudentAndSessionWithDetails(studentId, sessionId)
//                .orElseThrow(() -> new EntityNotFoundException("Student fee assignment not found"));
//
//        BigDecimal totalAssigned = assignment.getFeeStructure().getItems().stream()
//                .map(FeeStructureItem::getAmount)
//                .reduce(BigDecimal.ZERO, BigDecimal::add);
//
//        BigDecimal totalDiscount = assignment.getDiscountAmount();
//
//        BigDecimal totalPaid = paymentRepository.findByStudentFeeAssignmentIdOrderByPaymentDateDesc(assignment.getId())
//                .stream()
//                .filter(p -> p.getStatus() == PaymentStatus.PAID)
//                .map(FeePayment::getAmount)
//                .reduce(BigDecimal.ZERO, BigDecimal::add);
//
//        BigDecimal balance = totalAssigned.subtract(totalDiscount).subtract(totalPaid);
//
//        List<FeeHeadBalanceResponse> feeHeadBalances = assignment.getFeeStructure().getItems().stream()
//                .map(item -> {
//                    BigDecimal paid = paymentRepository.getTotalPaidByAssignmentAndFeeHead(
//                            assignment.getId(), item.getFeeHead().getId());
//                    if (paid == null) paid = BigDecimal.ZERO;
//
//                    return FeeHeadBalanceResponse.builder()
//                            .feeHeadId(item.getFeeHead().getId())
//                            .feeHeadName(item.getFeeHead().getName())
//                            .assigned(item.getAmount())
//                            .paid(paid)
//                            .balance(item.getAmount().subtract(paid))
//                            .dueDate(item.getDueDate())
//                            .build();
//                })
//                .collect(Collectors.toList());
//
//        return StudentFeeSummaryResponse.builder()
//                .studentId(studentId)
//                .studentName(assignment.getStudentEnrollment().getStudent().getFirstName() + " " +
//                        assignment.getStudentEnrollment().getStudent().getLastName())
//                .className(assignment.getFeeStructure().getSchoolClass().getName())
//                .sessionName(assignment.getFeeStructure().getSession().getName())
//                .totalAssigned(totalAssigned)
//                .totalDiscount(totalDiscount)
//                .totalPaid(totalPaid)
//                .balance(balance)
//                .feeHeadBalances(feeHeadBalances)
//                .build();
//    }
//
//    @Transactional(readOnly = true)
//    public Page<FeePaymentResponse> getPaymentHistory(Long studentId, Pageable pageable) {
//        Page<FeePayment> payments = paymentRepository.findByStudentIdWithDetails(studentId, pageable);
//        return payments.map(feePaymentMapper::toResponse);
//    }
//}
//
