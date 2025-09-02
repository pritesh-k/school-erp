//package com.schoolerp.mapper;
//
//import com.schoolerp.dto.response.FeeHeadBalanceResponse;
//import com.schoolerp.entity.FeePayment;
//import com.schoolerp.entity.FeeStructureItem;
//import com.schoolerp.enums.PaymentStatus;
//import org.mapstruct.Context;
//import org.mapstruct.Mapper;
//import org.mapstruct.Mapping;
//
//import java.math.BigDecimal;
//import java.util.List;
//
//@Mapper(componentModel = "spring")
//public interface FeeHeadBalanceMapper {
//
//    // This is a simpler mapper since FeeHeadBalanceResponse is typically a calculated DTO
//    // Used primarily for mapping calculated balance information
//
//    @Mapping(source = "feeHead.id", target = "feeHeadId")
//    @Mapping(source = "feeHead.name", target = "feeHeadName")
//    @Mapping(source = "structureItem.amount", target = "assigned")
//    @Mapping(source = "structureItem.dueDate", target = "dueDate")
//    @Mapping(target = "paid", expression = "java(calculatePaidAmount(payments, feeHead.getId()))")
//    @Mapping(target = "balance", expression = "java(calculateBalance(structureItem.getAmount(), calculatePaidAmount(payments, feeHead.getId())))")
//    FeeHeadBalanceResponse toResponse(FeeStructureItem structureItem,
//                                      @Context List<FeePayment> payments);
//
//    // Helper method for calculating paid amount for specific fee head
//    default BigDecimal calculatePaidAmount(List<FeePayment> payments, Long feeHeadId) {
//        if (payments == null || feeHeadId == null) return BigDecimal.ZERO;
//
//        return payments.stream()
//                .filter(payment -> payment.getFeeHead().getId().equals(feeHeadId))
//                .filter(payment -> payment.getStatus() == PaymentStatus.PAID)
//                .map(FeePayment::getAmount)
//                .reduce(BigDecimal.ZERO, BigDecimal::add);
//    }
//
//    // Helper method for calculating balance
//    default BigDecimal calculateBalance(BigDecimal assigned, BigDecimal paid) {
//        if (assigned == null) assigned = BigDecimal.ZERO;
//        if (paid == null) paid = BigDecimal.ZERO;
//        return assigned.subtract(paid);
//    }
//}
//
