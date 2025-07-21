package com.schoolerp.service.impl;

import com.schoolerp.dto.request.FeeRecordCreateDto;
import com.schoolerp.dto.response.FeeRecordResponseDto;
import com.schoolerp.entity.FeeRecord;
import com.schoolerp.entity.FeeType;
import com.schoolerp.entity.Student;
import com.schoolerp.enums.FeeStatus;
import com.schoolerp.exception.ResourceNotFoundException;
import com.schoolerp.mapper.FeeRecordMapper;
import com.schoolerp.repository.FeeRecordRepository;
import com.schoolerp.repository.FeeTypeRepository;
import com.schoolerp.repository.StudentRepository;
import com.schoolerp.service.FeeRecordService;
import com.schoolerp.utils.PdfUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FeeRecordServiceImpl implements FeeRecordService {

    private final FeeRecordRepository repo;
    private final StudentRepository studentRepo;
    private final FeeTypeRepository feeTypeRepo;
    private final FeeRecordMapper mapper;
    private final PdfUtil pdfUtil;

    @Override
    @Transactional
    public FeeRecordResponseDto create(FeeRecordCreateDto dto) {
        Student st = studentRepo.findById(dto.studentId())
                .orElseThrow(() -> new ResourceNotFoundException("Student not found"));
        FeeType ft = feeTypeRepo.findById(dto.feeTypeId())
                .orElseThrow(() -> new ResourceNotFoundException("Fee type not found"));

        FeeRecord record = FeeRecord.builder()
                .student(st)
                .feeType(ft)
                .amount(dto.amount())
                .dueDate(dto.dueDate())
                .status(FeeStatus.DUE)
                .build();
        return mapper.toDto(repo.save(record));
    }

    @Override
    public Page<FeeRecordResponseDto> list(Pageable p) {
        return repo.findAll(p).map(mapper::toDto);
    }

    @Override
    public List<FeeRecordResponseDto> byStudent(Long studentId) {
        return repo.findByStudentIdOrderByDueDateDesc(studentId)
                .stream().map(mapper::toDto).toList();
    }

    @Override
    public byte[] generateReceipt(Long feeRecordId) {
        FeeRecord fr = repo.findById(feeRecordId)
                .orElseThrow(() -> new ResourceNotFoundException("Fee record not found"));

        if (fr.getStatus() != FeeStatus.PAID)
            throw new IllegalStateException("Fee not paid yet");

        String receiptNo = UUID.randomUUID().toString();
        fr.setReceiptNo(receiptNo);
        repo.save(fr);

        return pdfUtil.generateReceipt(
                fr.getStudent().getFirstName(),
                fr.getFeeType().getName(),
                fr.getAmount().toString()
        );
    }
}