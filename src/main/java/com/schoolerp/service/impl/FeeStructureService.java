package com.schoolerp.service.impl;

import com.schoolerp.dto.request.FeeStructureRequest;
import com.schoolerp.dto.response.FeeStructureResponse;
import com.schoolerp.entity.AcademicSession;
import com.schoolerp.entity.FeeStructure;
import com.schoolerp.entity.SchoolClass;
import com.schoolerp.mapper.FeeStructureMapper;
import com.schoolerp.repository.AcademicSessionRepository;
import com.schoolerp.repository.FeeHeadRepository;
import com.schoolerp.repository.FeeStructureRepository;
import com.schoolerp.repository.SchoolClassRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Service
@Transactional
public class FeeStructureService {

    @Autowired
    private FeeStructureRepository feeStructureRepository;
    @Autowired
    private FeeHeadRepository feeHeadRepository;
    @Autowired
    private AcademicSessionRepository sessionRepository;
    @Autowired
    private SchoolClassRepository classRepository;
    @Autowired
    FeeStructureMapper feeStructureMapper;

    public FeeStructureResponse create(FeeStructureRequest request, Long createdBy, String academicSessionName) {
        AcademicSession academicSession = sessionRepository.findByName(academicSessionName)
                .orElseThrow(() -> new EntityNotFoundException("Academic session not found"));
        SchoolClass schoolClass = classRepository.findById(request.getClassId())
                .orElseThrow(() -> new EntityNotFoundException("Class not found"));

        FeeStructure feeStructure = FeeStructure.builder()
                .schoolClass(schoolClass)
                .name(request.getName())
                .session(academicSession)
                .build();

        feeStructure.setActive(true);
        feeStructure.setCreatedAt(Instant.now());
        feeStructure.setCreatedBy(createdBy);

        feeStructure = feeStructureRepository.save(feeStructure);

        return feeStructureMapper.toResponse(feeStructure);
    }

    @Transactional(readOnly = true)
    public Page<FeeStructureResponse> list(Pageable pageable, Long sessionId, Long classId) {
        Page<FeeStructure> structures = feeStructureRepository
                .findBySessionIdAndSchoolClassIdOrderByNameAsc(pageable, sessionId, classId);
        return structures.map(feeStructureMapper::toResponse);
    }

    @Transactional(readOnly = true)
    public FeeStructureResponse getById(Long id) {
        FeeStructure structure = feeStructureRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("FeeStructure not found with id: " + id));
        return feeStructureMapper.toResponse(structure);
    }
}
