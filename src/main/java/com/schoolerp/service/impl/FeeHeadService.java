package com.schoolerp.service.impl;

import com.schoolerp.dto.request.feeHead.FeeHeadRequest;
import com.schoolerp.entity.FeeHead;
import com.schoolerp.exception.ResourceNotFoundException;
import com.schoolerp.mapper.FeeHeadMapper;
import com.schoolerp.repository.FeeHeadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FeeHeadService {
    @Autowired
    private FeeHeadRepository feeHeadRepository;

    @Autowired
    FeeHeadMapper feeHeadMapper;

    public FeeHeadRequest create(FeeHeadRequest head) {
        FeeHead feeHead = new FeeHead();
        feeHead.setName(head.getName());
        feeHeadRepository.save(feeHead);
        return head;
    }
    public FeeHead get(Long feeHeadId) { return feeHeadRepository.findById(feeHeadId).orElseThrow(() -> new ResourceNotFoundException("FeeHead")); }
    public Page<FeeHeadRequest> list(Pageable pageable) {
        Page<FeeHead> feeHeads = feeHeadRepository.findAll(pageable);
        return feeHeads.map(feeHeadMapper::toDto);
    }
    public FeeHead update(Long id, FeeHead updated) {
        FeeHead head = get(id);
        head.setName(updated.getName());
        return feeHeadRepository.save(head);
    }
    public void delete(Long id) { feeHeadRepository.deleteById(id); }
}

