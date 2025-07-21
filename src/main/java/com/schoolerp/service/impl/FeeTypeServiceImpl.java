package com.schoolerp.service.impl;

import com.schoolerp.dto.request.FeeTypeCreateDto;
import com.schoolerp.dto.response.FeeTypeResponseDto;
import com.schoolerp.entity.FeeType;
import com.schoolerp.mapper.FeeTypeMapper;
import com.schoolerp.repository.FeeTypeRepository;
import com.schoolerp.service.FeeTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class FeeTypeServiceImpl implements FeeTypeService {

    private final FeeTypeRepository repo;
    private final FeeTypeMapper mapper;

    @Override
    @Transactional
    public FeeTypeResponseDto create(FeeTypeCreateDto dto) {
        FeeType ft = FeeType.builder()
                .name(dto.name())
                .defaultAmount(dto.defaultAmount())
                .build();
        return mapper.toDto(repo.save(ft));
    }

    @Override
    public Page<FeeTypeResponseDto> list(Pageable p) {
        return repo.findAll(p).map(mapper::toDto);
    }
}