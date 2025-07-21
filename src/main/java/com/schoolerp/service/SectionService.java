package com.schoolerp.service;

import com.schoolerp.entity.Section;
import java.util.List;

public interface SectionService {
    List<Section> getAllSections();
    List<Section> getSectionsByClass(Long classId);
}