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
    FeePaymentResponse toResponse(FeePayment payment);
}

