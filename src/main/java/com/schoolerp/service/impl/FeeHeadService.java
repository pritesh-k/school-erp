package com.schoolerp.service.impl;

import com.schoolerp.dto.request.FeeHeadRequest;
import com.schoolerp.dto.request.FeeHeadUpdateRequest;
import com.schoolerp.dto.response.FeeHeadResponse;
import com.schoolerp.entity.FeeHead;
import com.schoolerp.enums.FeeCategory;
import com.schoolerp.mapper.FeeHeadMapper;
import com.schoolerp.repository.FeeHeadRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class FeeHeadService {
    @Autowired
    private FeeHeadRepository feeHeadRepository;

    @Autowired
    FeeHeadMapper feeHeadMapper;

    public FeeHeadResponse create(FeeHeadRequest request, Long createdBy) {
        FeeHead feeHead = FeeHead.builder()
                .name(request.getName())
                .description(request.getDescription())
                .category(request.getCategory())
                .isActive(true)
                .build();

        feeHead.setMandatory(request.getMandatory());

        feeHead.setActive(true);
        feeHead.setCreatedBy(createdBy);
        feeHead.setCreatedAt(Instant.now());
        feeHead = feeHeadRepository.save(feeHead);
        return feeHeadMapper.toResponse(feeHead);
    }

    @Transactional(readOnly = true)
    public Page<FeeHeadResponse> list(Pageable pageable) {
        Page<FeeHead> feeHeads = feeHeadRepository.findActive(pageable);
        return feeHeads.map(feeHeadMapper::toResponse);
    }

    @Transactional(readOnly = true)
    public FeeHeadResponse getById(Long id) {
        FeeHead feeHead = feeHeadRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("FeeHead not found with id: " + id));
        return feeHeadMapper.toResponse(feeHead);
    }

    public FeeHeadResponse update(Long id, FeeHeadUpdateRequest request, Long updatedBy) {
        FeeHead feeHead = feeHeadRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("FeeHead not found with id: " + id));
        boolean hasChanges = false;

        if (feeHead.getName() != null && !feeHead.getName().equals(request.getName())) {
            feeHead.setName(request.getName());
            hasChanges = true;
        }
        if (request.getDescription() != null){
            feeHead.setDescription(request.getDescription());
            hasChanges = true;
        }
        if (request.getCategory() != null){
            feeHead.setCategory(request.getCategory());
            hasChanges = true;
        }

        if (request.getMandatory() != null) {
            feeHead.setMandatory(request.getMandatory());
            hasChanges = true;
        }

        if (hasChanges) {
            feeHead.setUpdatedBy(updatedBy);
            feeHead.setUpdatedAt(Instant.now());
            feeHead = feeHeadRepository.save(feeHead);
        }
        return feeHeadMapper.toResponse(feeHead);
    }
}

