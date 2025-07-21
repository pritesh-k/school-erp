package com.schoolerp.service;

import com.schoolerp.dto.request.FeeRecordCreateDto;
import com.schoolerp.dto.response.FeeRecordResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface FeeRecordService {
    FeeRecordResponseDto create(FeeRecordCreateDto dto);
    Page<FeeRecordResponseDto> list(Pageable p);
    List<FeeRecordResponseDto> byStudent(Long studentId);
    byte[] generateReceipt(Long feeRecordId);
}