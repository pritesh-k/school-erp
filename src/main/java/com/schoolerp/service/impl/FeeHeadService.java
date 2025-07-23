package com.schoolerp.service.impl;

import com.schoolerp.entity.FeeHead;
import com.schoolerp.exception.ResourceNotFoundException;
import com.schoolerp.repository.FeeHeadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FeeHeadService {
    @Autowired
    private FeeHeadRepository feeHeadRepository;

    public FeeHead create(FeeHead head) { return feeHeadRepository.save(head); }
    public FeeHead get(Long id) { return feeHeadRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("FeeHead")); }
    public List<FeeHead> list() { return feeHeadRepository.findAll(); }
    public FeeHead update(Long id, FeeHead updated) {
        FeeHead head = get(id);
        head.setName(updated.getName());
        return feeHeadRepository.save(head);
    }
    public void delete(Long id) { feeHeadRepository.deleteById(id); }
}

