//package com.schoolerp.mapper;
//
//import com.schoolerp.dto.response.FeeHeadBalanceResponse;
//import com.schoolerp.dto.response.StudentFeeSummaryResponse;
//import com.schoolerp.entity.FeePayment;
//import com.schoolerp.entity.FeeStructureItem;
//import com.schoolerp.entity.Student;
//import com.schoolerp.entity.StudentFeeAssignment;
//import com.schoolerp.enums.PaymentStatus;
//import org.mapstruct.Mapper;
//import org.mapstruct.Mapping;
//import org.mapstruct.Named;
//
//import java.math.BigDecimal;
//import java.util.List;
//import java.util.stream.Collectors;
//
//@Mapper(componentModel = "spring", uses = {FeeHeadBalanceMapper.class})
//public interface StudentFeeSummaryMapper {
//
//    @Mapping(source = "studentEnrollment.student.id", target = "studentId")
//    @Mapping(source = "studentEnrollment.student", target = "studentName", qualifiedByName = "studentToName")
//    @Mapping(source = "feeStructure.schoolClass.name", target = "className")
//    @Mapping(source = "feeStructure.session.name", target = "sessionName")
//    @Mapping(target = "totalAssigned", expression = "java(calculateTotalAssigned(assignment))")
//    @Mapping(source = "discountAmount", target = "totalDiscount")
//    @Mapping(target = "totalPaid", expression = "java(calculateTotalPaid(assignment))")
//    @Mapping(target = "balance", expression = "java(calculateBalance(assignment))")
//    @Mapping(target = "feeHeadBalances", expression = "java(mapFeeHeadBalances(assignment))")
//    StudentFeeSummaryResponse toResponse(StudentFeeAssignment assignment);
//
//    @Named("studentToName")
//    default String studentToName(Student student) {
//        if (student == null) return null;
//        return student.getFirstName() + " " + student.getLastName();
//    }
//
//    // Custom calculation methods
//    default BigDecimal calculateTotalAssigned(StudentFeeAssignment assignment) {
//        return assignment.getFeeStructure().getItems().stream()
//                .map(FeeStructureItem::getAmount)
//                .reduce(BigDecimal.ZERO, BigDecimal::add);
//    }
//
//    default BigDecimal calculateTotalPaid(StudentFeeAssignment assignment) {
//        return assignment.getPayments().stream()
//                .filter(payment -> payment.getStatus() == PaymentStatus.PAID)
//                .map(FeePayment::getAmount)
//                .reduce(BigDecimal.ZERO, BigDecimal::add);
//    }
//
//    default BigDecimal calculateBalance(StudentFeeAssignment assignment) {
//        BigDecimal totalAssigned = calculateTotalAssigned(assignment);
//        BigDecimal totalPaid = calculateTotalPaid(assignment);
//        BigDecimal discount = assignment.getDiscountAmount() != null ?
//                assignment.getDiscountAmount() : BigDecimal.ZERO;
//        return totalAssigned.subtract(discount).subtract(totalPaid);
//    }
//
//    default List<FeeHeadBalanceResponse> mapFeeHeadBalances(StudentFeeAssignment assignment) {
//        return assignment.getFeeStructure().getItems().stream()
//                .map(item -> {
//                    BigDecimal paid = assignment.getPayments().stream()
//                            .filter(payment -> payment.getFeeHead().getId().equals(item.getFeeHead().getId()))
//                            .filter(payment -> payment.getStatus() == PaymentStatus.PAID)
//                            .map(FeePayment::getAmount)
//                            .reduce(BigDecimal.ZERO, BigDecimal::add);
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
//    }
//}
//
