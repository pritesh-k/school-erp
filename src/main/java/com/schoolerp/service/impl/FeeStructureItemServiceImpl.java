package com.schoolerp.service.impl;

import com.schoolerp.dto.request.FeeStructureItemRequest;
import com.schoolerp.dto.request.FeeStructureItemUpdateRequest;
import com.schoolerp.dto.response.FeeStructureItemResponse;
import com.schoolerp.entity.FeeHead;
import com.schoolerp.entity.FeeStructure;
import com.schoolerp.entity.FeeStructureItem;
import com.schoolerp.exception.DuplicateEntry;
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

        if (feeStructureItemRepository.existsByFeeStructure_IdAndFeeHead_Id(feeStructureId, feeHead.getId())) {
            throw new DuplicateEntry("FeeStructureItem with the same FeeHead already exists in this FeeStructure");
        }

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

    public FeeStructureItemResponse update(Long feeStructureItemId, FeeStructureItemUpdateRequest request, Long updatedBy) {

        FeeStructureItem existing = feeStructureItemRepository.findById(feeStructureItemId)
                .orElseThrow(() -> new ResourceNotFoundException("FeeStructureItem not found"));
        boolean changed = false;

        if (request.getAmount() != null) {
            existing.setAmount(request.getAmount());
            changed = true;
        }
        if (request.getDueDate() != null) {
            existing.setDueDate(request.getDueDate());
            changed = true;
        }
        if (!changed) {
            return mapper.toResponse(existing);
        }

        existing.setUpdatedBy(updatedBy);
        existing.setUpdatedAt(Instant.now());

        FeeStructureItem saved = feeStructureItemRepository.save(existing);
        return mapper.toResponse(saved);
    }

    public Page<FeeStructureItemResponse> list(Pageable pageable, Long feeStructureId) {
        Page<FeeStructureItem> page = feeStructureItemRepository.findByFeeStructure_Id(feeStructureId, pageable);
        return page.map(mapper::toResponse);
    }

    public FeeStructureItemResponse getById(Long id) {
        FeeStructureItem entity = feeStructureItemRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("FeeStructureItem not found"));
        return mapper.toResponse(entity);
    }
}

