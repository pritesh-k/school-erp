package com.schoolerp.service;

import com.schoolerp.dto.request.FeeTypeCreateDto;
import com.schoolerp.dto.response.FeeTypeResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface FeeTypeService {
    FeeTypeResponseDto create(FeeTypeCreateDto dto);
    Page<FeeTypeResponseDto> list(Pageable p);
}