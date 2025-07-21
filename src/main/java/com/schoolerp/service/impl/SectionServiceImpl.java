package com.schoolerp.service.impl;

import com.schoolerp.entity.Section;
import com.schoolerp.repository.SectionRepository;
import com.schoolerp.service.SectionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SectionServiceImpl implements SectionService {
    private final SectionRepository sectionRepository;

    @Override
    public List<Section> getAllSections() {
        return sectionRepository.findAll();
    }

    @Override
    public List<Section> getSectionsByClass(Long classId) {
        return sectionRepository.findBySchoolClassId(classId);
    }
}