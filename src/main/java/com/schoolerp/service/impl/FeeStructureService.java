package com.schoolerp.service.impl;

import com.schoolerp.dto.request.FeeStructureRequest;
import com.schoolerp.dto.request.FeeStructureUpdateRequest;
import com.schoolerp.dto.response.FeeStructureResponse;
import com.schoolerp.entity.AcademicSession;
import com.schoolerp.entity.FeeStructure;
import com.schoolerp.entity.FeeStructureItem;
import com.schoolerp.entity.SchoolClass;
import com.schoolerp.exception.DuplicateEntry;
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
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

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
        if (feeStructureRepository.existsByAcademicSession_IdAndSchoolClass_Id(academicSession.getId(), schoolClass.getId())) {
            throw new DuplicateEntry("Fee structure already exists for this class and session");
        }
        FeeStructure feeStructure = FeeStructure.builder()
                .schoolClass(schoolClass)
                .name(request.getName())
                .academicSession(academicSession)
                .build();

        feeStructure.setActive(true);
        feeStructure.setCreatedAt(Instant.now());
        feeStructure.setCreatedBy(createdBy);
        feeStructure.setDeleted(false);

        feeStructure = feeStructureRepository.save(feeStructure);

        return feeStructureMapper.toResponse(feeStructure);
    }

    @Transactional
    public FeeStructureResponse migrateToNextSession(long feeStructureId, long academicSessionId, long createdBy) {
        if (feeStructureId <= 0 || academicSessionId <= 0) {
            throw new IllegalArgumentException("Invalid feeStructureId or academicSessionId");
        }

        AcademicSession academicSession = sessionRepository.findById(academicSessionId)
                .orElseThrow(() -> new EntityNotFoundException("Academic session not found"));

        FeeStructure existingStructure = feeStructureRepository.findById(feeStructureId)
                .orElseThrow(() -> new EntityNotFoundException("Fee structure not found"));

        if (existingStructure.getAcademicSession().getId().equals(academicSessionId)) {
            throw new DuplicateEntry("Cannot copy to the same academic session");
        }

        FeeStructure newStructure = new FeeStructure();
        newStructure.setSchoolClass(existingStructure.getSchoolClass());
        newStructure.setName(existingStructure.getName());
        newStructure.setAcademicSession(academicSession);
        newStructure.setActive(true);
        newStructure.setCreatedAt(Instant.now());
        newStructure.setCreatedBy(createdBy);
        newStructure.setDeleted(false);

        if (existingStructure.getItems() != null && !existingStructure.getItems().isEmpty()) {
            Set<FeeStructureItem> newItems = existingStructure.getItems().stream()
                    .map(item -> {
                        FeeStructureItem copy = new FeeStructureItem();
                        copy.setFeeStructure(newStructure);   // link to new parent
                        copy.setFeeHead(item.getFeeHead());   // reuse same FeeHead
                        copy.setAmount(item.getAmount());     // copy amount
                        copy.setDueDate(item.getDueDate());   // copy due date
                        return copy;
                    })
                    .collect(Collectors.toSet());

            newStructure.setItems(newItems);
        }

        FeeStructure saved = feeStructureRepository.save(newStructure);

        return feeStructureMapper.toResponse(saved);
    }


    public Page<FeeStructureResponse> list(Pageable pageable, String sessionName) {
        AcademicSession session = sessionRepository.findByName(sessionName)
                .orElseThrow(() -> new EntityNotFoundException("Academic session not found"));
        Page<FeeStructure> structures = feeStructureRepository
                .findByAcademicSession_Id(pageable, session.getId());

        return structures.map(feeStructureMapper::toResponse);
    }

    public FeeStructureResponse getById(Long id) {
        FeeStructure structure = feeStructureRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("FeeStructure not found with id: " + id));
        return feeStructureMapper.toResponse(structure);
    }

    public FeeStructureResponse getFeeStructureByClass(Long id) {
        FeeStructure structure = feeStructureRepository.findBySchoolClass_Id(id)
                .orElseThrow(() -> new EntityNotFoundException("FeeStructure not found for class id: " + id));
        return feeStructureMapper.toResponse(structure);
    }

    public FeeStructureResponse update(FeeStructureUpdateRequest request, Long updatedBy, Long id) {
        FeeStructure feeStructure = feeStructureRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("FeeStructure not found with id: " + id));
        if (request.getName() != null && !request.getName().isBlank() && !request.getName().equals(feeStructure.getName())){
            feeStructure.setName(request.getName());
            feeStructure.setUpdatedAt(Instant.now());
            feeStructure.setUpdatedBy(updatedBy);
            feeStructure = feeStructureRepository.save(feeStructure);
        }
        return feeStructureMapper.toResponse(feeStructure);
    }
}
