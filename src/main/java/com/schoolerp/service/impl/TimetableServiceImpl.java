package com.schoolerp.service.impl;

import com.schoolerp.dto.request.CreateTimetableDTO;
import com.schoolerp.dto.request.UpdateTimetableDTO;
import com.schoolerp.dto.response.TimetableDetailedResponseDTO;
import com.schoolerp.dto.response.TimetableResponseDTO;
import com.schoolerp.entity.AcademicSession;
import com.schoolerp.entity.SectionSubjectAssignment;
import com.schoolerp.entity.Timetable;
import com.schoolerp.enums.TimetableType;
import com.schoolerp.exception.ResourceNotFoundException;
import com.schoolerp.mapper.TimetableMapper;
import com.schoolerp.repository.AcademicSessionRepository;
import com.schoolerp.repository.SectionSubjectAssignmentRepository;
import com.schoolerp.repository.TimetableRepository;
import com.schoolerp.service.TimetableService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class TimetableServiceImpl implements TimetableService {

    private final TimetableRepository timetableRepository;
    private final TimetableMapper timetableMapper;
    private final AcademicSessionRepository aca;
    private final SectionSubjectAssignmentRepository sectionSubjectAssignmentRepository;

    @Override
    public TimetableResponseDTO createTimetable(CreateTimetableDTO dto, Long createdByUserId) {
        Timetable entity = new Timetable();

        entity.setStatus(Timetable.TimeTableStatus.DRAFT);
        entity.setCreatedBy(createdByUserId); // from BaseEntity
        entity.setUpdatedBy(createdByUserId);

        Timetable saved = timetableRepository.save(entity);
        return timetableMapper.toResponseDto(saved);
    }

    @Override
    public TimetableResponseDTO createTimetableForSubject(Long createdByUserId,
                                                          Long sectionSubjectAssignmentId) {
        Timetable entity = new Timetable();
        entity.setStatus(Timetable.TimeTableStatus.DRAFT);
        entity.setType(TimetableType.PERIOD);
        entity.setCreatedBy(createdByUserId);
        entity.setUpdatedBy(createdByUserId);
        entity.setActive(true);
        entity.setDeleted(false);

        SectionSubjectAssignment sectionSubjectAssignment = sectionSubjectAssignmentRepository
                .findById(sectionSubjectAssignmentId)
                .orElseThrow(() -> new ResourceNotFoundException("SectionSubjectAssignment not found"));
        AcademicSession academicSession = sectionSubjectAssignment.getAcademicSession();
        entity.setAcademicSession(academicSession);
        entity.setSectionSubjectAssignment(sectionSubjectAssignment);

        Timetable saved = timetableRepository.save(entity);
        return timetableMapper.toResponseDto(saved);
    }

    @Override
    public TimetableResponseDTO updateTimetable(Long id, UpdateTimetableDTO dto, Long updatedByUserId) {
        Timetable entity = timetableRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Timetable not found"));

        entity.setUpdatedBy(updatedByUserId);

        Timetable updated = timetableRepository.save(entity);
        return timetableMapper.toResponseDto(updated);
    }

    @Override
    public TimetableResponseDTO activateTimetable(Long id, Long updatedByUserId) {
        Timetable entity = timetableRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Timetable not found"));

        entity.setStatus(Timetable.TimeTableStatus.ACTIVE);
        entity.setUpdatedBy(updatedByUserId);

        Timetable updated = timetableRepository.save(entity);
        return timetableMapper.toResponseDto(updated);
    }

    @Override
    public void deleteTimetable(Long id, Long deletedByUserId) {
        Timetable entity = timetableRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Timetable not found"));

        entity.setUpdatedBy(deletedByUserId);
        timetableRepository.delete(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public TimetableDetailedResponseDTO getTimetableById(Long id) {
        Timetable entity = timetableRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Timetable not found"));
        return timetableMapper.toDetailedResponse(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TimetableDetailedResponseDTO> getAllTimetables(String academicSessionName, Pageable pageable) {
        AcademicSession academicSession = aca.findByName(academicSessionName)
                .orElseThrow(() -> new ResourceNotFoundException("Academic Session not found"));
        Page<Timetable> page = timetableRepository.findByAcademicSession_Id(academicSession.getId(), pageable);
        List<TimetableDetailedResponseDTO> mapped = page.stream()
                .map(timetableMapper::toDetailedResponse)
                .toList();
        return new PageImpl<>(mapped, pageable, page.getTotalElements());
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TimetableDetailedResponseDTO> searchTimetables(Long classId,
                                                               Long sectionId,
                                                               Long subjectId,
                                                               String academicSessionName,
                                                               Pageable pageable) {
        Page<Timetable> page = timetableRepository.searchTimetables(classId, sectionId, subjectId, academicSessionName, pageable);

        List<TimetableDetailedResponseDTO> mapped = page.stream()
                .map(timetableMapper::toDetailedResponse)
                .toList();

        return new PageImpl<>(mapped, pageable, page.getTotalElements());
    }

}

