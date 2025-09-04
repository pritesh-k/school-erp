package com.schoolerp.mapper;

import com.schoolerp.dto.request.RecordPaymentRequest;
import com.schoolerp.dto.response.FeePaymentResponse;
import com.schoolerp.entity.FeeHead;
import com.schoolerp.entity.FeePayment;
import com.schoolerp.entity.StudentFeeAssignment;
import com.schoolerp.enums.PaymentMode;
import com.schoolerp.enums.PaymentStatus;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring", uses = {StudentFeeAssignmentMapper.class, FeeHeadMapper.class})
public interface FeePaymentMapper {

    // Entity to Response DTO
    @Mapping(source = "studentFeeAssignment", target = "studentFeeAssignmentId", qualifiedByName = "assignmentToId")
    @Mapping(source = "feeHead", target = "feeHeadId", qualifiedByName = "feeHeadToId")
    @Mapping(source = "feeHead", target = "feeHeadName", qualifiedByName = "feeHeadToName")
    @Mapping(source = "paymentMode", target = "paymentMode", qualifiedByName = "paymentModeToString")
    @Mapping(source = "status", target = "status", qualifiedByName = "statusToString")
    FeePaymentResponse toResponse(FeePayment payment);

    // Custom mapping methods
    @Named("assignmentToId")
    default Long assignmentToId(StudentFeeAssignment assignment) {
        return assignment != null ? assignment.getId() : null;
    }

    @Named("feeHeadToId")
    default Long feeHeadToId(FeeHead feeHead) {
        return feeHead != null ? feeHead.getId() : null;
    }

    @Named("feeHeadToName")
    default String feeHeadToName(FeeHead feeHead) {
        return feeHead != null ? feeHead.getName() : null;
    }

    @Named("paymentModeToString")
    default String paymentModeToString(PaymentMode mode) {
        return mode != null ? mode.name() : null;
    }

    @Named("statusToString")
    default String statusToString(PaymentStatus status) {
        return status != null ? status.name() : null;
    }

    @Named("idToAssignment")
    default StudentFeeAssignment idToAssignment(Long assignmentId) {
        if (assignmentId == null) return null;
        StudentFeeAssignment studentFeeAssignment = new StudentFeeAssignment();
        studentFeeAssignment.setId(assignmentId);
        return studentFeeAssignment;
    }

    @Named("idToFeeHead")
    default FeeHead idToFeeHead(Long feeHeadId) {
        if (feeHeadId == null) return null;
        FeeHead feeHead = new FeeHead();
        feeHead.setId(feeHeadId);
        return feeHead;
    }

    @Named("stringToPaymentMode")
    default PaymentMode stringToPaymentMode(String mode) {
        return mode != null ? PaymentMode.valueOf(mode) : null;
    }
}

