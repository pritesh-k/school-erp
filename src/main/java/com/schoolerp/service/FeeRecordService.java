package com.schoolerp.service;

import com.schoolerp.dto.request.FeePaymentDto;
import com.schoolerp.dto.request.FeeRecordCreateDto;
import com.schoolerp.dto.response.FeeRecordResponseDto;
import com.schoolerp.entity.FeeRecord;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface FeeRecordService {
    FeeRecord record(FeePaymentDto dto);
    List<FeeRecord> listByAssignment(Long assignmentId);
    void delete(Long id);
}