package com.schoolerp.service.impl;

import com.schoolerp.dto.request.FeeItem;
import com.schoolerp.dto.request.FeeStructureDto;
import com.schoolerp.entity.*;
import com.schoolerp.repository.AcademicSessionRepository;
import com.schoolerp.repository.FeeHeadRepository;
import com.schoolerp.repository.FeeStructureRepository;
import com.schoolerp.repository.SchoolClassRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class FeeStructureService {
    @Autowired private FeeStructureRepository repo;
    @Autowired private SchoolClassRepository classRepo;
    @Autowired private AcademicSessionRepository sessionRepo;
    @Autowired private FeeHeadRepository headRepo;

    public FeeStructure create(FeeStructureDto dto) {
        AcademicSession session = sessionRepo.findById(dto.getSessionId()).orElseThrow();
        SchoolClass schoolClass = classRepo.findById(dto.getSchoolClassId()).orElseThrow();

        FeeStructure structure = new FeeStructure();
        structure.setSession(session);
        structure.setSchoolClass(schoolClass);

        Set<FeeStructureItem> items = new HashSet<>();
        dto.getItems().forEach(itemDto -> {
            FeeHead head = headRepo.findById(itemDto.getHeadId()).orElseThrow();
            FeeStructureItem item = new FeeStructureItem();
            item.setFeeStructure(structure);
            item.setFeeHead(head);
            items.add(item);
        });
        structure.setItems(items);
        return repo.save(structure);
    }
    public FeeStructure get(Long id) { return repo.findById(id).orElseThrow(); }
    public List<FeeStructure> listByClassAndSession(Long classId, Long sessionId) {
        return repo.findAll((root, query, cb) -> cb.and(
                cb.equal(root.get("schoolClass").get("id"), classId),
                cb.equal(root.get("session").get("id"), sessionId)
        ));
    }
    public FeeStructure update(Long id, FeeStructureDto dto) {
        FeeStructure fs = get(id);
        // update logic as needed, similar to create
        return repo.save(fs);
    }
    public void delete(Long id) { repo.deleteById(id); }
}
