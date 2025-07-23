package com.schoolerp.service.impl;

import com.schoolerp.dto.request.FeePaymentDto;
import com.schoolerp.entity.FeeHead;
import com.schoolerp.entity.FeeRecord;
import com.schoolerp.entity.StudentFeeAssignment;
import com.schoolerp.enums.FeeStatus;
import com.schoolerp.repository.FeeHeadRepository;
import com.schoolerp.repository.FeeRecordRepository;
import com.schoolerp.repository.StudentFeeAssignmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
public class FeeRecordService {
    @Autowired private StudentFeeAssignmentRepository assignmentRepo;
    @Autowired private FeeHeadRepository headRepo;
    @Autowired private FeeRecordRepository recordRepo;

    public FeeRecord record(FeePaymentDto dto) {
        StudentFeeAssignment assignment = assignmentRepo.findById(dto.getStudentFeeAssignmentId()).orElseThrow();
        FeeHead head = headRepo.findById(dto.getFeeHeadId()).orElseThrow();
        FeeRecord rec = FeeRecord.builder()
                .studentFeeAssignment(assignment)
                .feeHead(head)
                .paidAmount(dto.getPaidAmount())
                .paidDate(LocalDate.now())
                .note(dto.getNote())
                .status(FeeStatus.PAID)
                .build();
        return recordRepo.save(rec);
    }
    public List<FeeRecord> listByAssignment(Long assignmentId) {
        return recordRepo.findByStudentFeeAssignmentId(assignmentId);
    }
    public void delete(Long id) { recordRepo.deleteById(id); }
}
