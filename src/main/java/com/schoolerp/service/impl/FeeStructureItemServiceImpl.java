package com.schoolerp.service.impl;

import com.schoolerp.dto.request.FeeStructureItemRequest;
import com.schoolerp.dto.response.FeeStructureItemResponse;
import com.schoolerp.entity.FeeHead;
import com.schoolerp.entity.FeeStructure;
import com.schoolerp.entity.FeeStructureItem;
import com.schoolerp.exception.ResourceNotFoundException;
import com.schoolerp.mapper.FeeStructureItemMapper;
import com.schoolerp.repository.FeeHeadRepository;
import com.schoolerp.repository.FeeStructureItemRepository;
import com.schoolerp.repository.FeeStructureRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Service
@RequiredArgsConstructor
@Transactional
public class FeeStructureItemServiceImpl {

    private final FeeStructureItemRepository feeStructureItemRepository;
    private final FeeStructureRepository feeStructureRepository;
    private final FeeHeadRepository feeHeadRepository;
    private final FeeStructureItemMapper mapper;

    public FeeStructureItemResponse create(Long feeStructureId, FeeStructureItemRequest request, Long createdBy) {
        // Validate parent FeeStructure
        FeeStructure feeStructure = feeStructureRepository.findById(feeStructureId)
                .orElseThrow(() -> new ResourceNotFoundException("FeeStructure not found"));

        // Validate FeeHead
        FeeHead feeHead = feeHeadRepository.findById(request.getFeeHeadId())
                .orElseThrow(() -> new ResourceNotFoundException("FeeHead not found"));

        // Map request -> entity
        FeeStructureItem entity = new FeeStructureItem();
        entity.setAmount(request.getAmount());
        entity.setDueDate(request.getDueDate());

        entity.setFeeStructure(feeStructure);
        entity.setFeeHead(feeHead);
        entity.setCreatedBy(createdBy);
        entity.setCreatedAt(Instant.now());

        FeeStructureItem saved = feeStructureItemRepository.save(entity);
        return mapper.toResponse(saved);
    }

    @Transactional(readOnly = true)
    public Page<FeeStructureItemResponse> list(Pageable pageable, Long feeStructureId) {
        Page<FeeStructureItem> page = feeStructureItemRepository.findAllByFilter(feeStructureId, pageable);
        return page.map(mapper::toResponse);
    }

    @Transactional(readOnly = true)
    public FeeStructureItemResponse getById(Long id) {
        FeeStructureItem entity = feeStructureItemRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("FeeStructureItem not found"));
        return mapper.toResponse(entity);
    }
}

