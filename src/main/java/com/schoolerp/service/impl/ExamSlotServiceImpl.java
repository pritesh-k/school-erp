package com.schoolerp.service.impl;

import com.schoolerp.dto.request.ExamSlotCreateDto;
import com.schoolerp.dto.request.ExamSlotUpdateDto;
import com.schoolerp.dto.response.ExamSlotResponseDto;
import com.schoolerp.entity.*;
import com.schoolerp.exception.ResourceNotFoundException;
import com.schoolerp.mapper.ExamSlotMapper;
import com.schoolerp.repository.*;
import com.schoolerp.service.ExamSlotService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class ExamSlotServiceImpl implements ExamSlotService {
    private final ExamSlotRepository repo;
    private final ExamSlotMapper mapper;
    private final SubjectRepository subjectRepository;
    private final SchoolClassRepository schoolClassRepository;
    private final ExamRepository examRepository;

    private final SectionRepository sectionRepository;

    @Override
    @Transactional
    public ExamSlotResponseDto create(ExamSlotCreateDto dto, Long examId, Long createById) {

        Subject subject = subjectRepository.findById(dto.getSubjectId())
                .orElseThrow(() -> new ResourceNotFoundException("Subject with id " + dto.getSubjectId() + " not found"));
        SchoolClass schoolClass = schoolClassRepository.findById(dto.getSchoolClassId())
                .orElseThrow(() -> new ResourceNotFoundException("SchoolClass with id " + dto.getSchoolClassId() + " not found"));
        ExamSlot examSlot = new ExamSlot();
        examSlot.setDate(dto.getDate());
        examSlot.setStartTime(dto.getStartTime());
        examSlot.setDurationMinutes(dto.getDurationMinutes());
        examSlot.setPractical(dto.getPractical() != null && dto.getPractical());
        examSlot.setSubject(subject);
        examSlot.setSchoolClass(schoolClass);
        examSlot.setMaxMarks( dto.getMaxMarks());

        if (dto.getSectionId() != null && !dto.getSectionId().equals(0L)){
            Section section = sectionRepository.findById(dto.getSectionId())
                    .orElseThrow(() -> new ResourceNotFoundException("Section with id " + dto.getSectionId() + " not found"));
            examSlot.setSection(section);
        }
        examSlot.setCreatedAt(Instant.now());
        examSlot.setCreatedBy(createById);
        examSlot.setActive(true);
        examSlot.setDeleted(false);

        Exam exam = examRepository.findById(examId)
                .orElseThrow(() -> new ResourceNotFoundException("Exam with id " + examId + " not found"));
        examSlot.setExam(exam);

        return mapper.toDto(repo.save(examSlot));
    }

    @Override
    @Transactional
    public ExamSlotResponseDto update(Long id, ExamSlotUpdateDto dto, Long updatedById) {
        ExamSlot slot = repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("ExamSlot with id " + id + " not found"));
        boolean hasChanges = false;
        if (dto.getSubjectId() != null && !dto.getSubjectId().equals(0L) && !dto.getSubjectId().equals(slot.getSubject().getId())) {
            Subject subject = subjectRepository.findById(dto.getSubjectId())
                    .orElseThrow(() -> new ResourceNotFoundException("Subject with id " + dto.getSubjectId() + " not found"));
            slot.setSubject(subject);
            hasChanges = true;
        }
        if (dto.getPractical() != null && !dto.getPractical().equals(slot.getPractical()) && !dto.getPractical().equals(slot.getPractical())) {
            slot.setPractical(dto.getPractical());
            hasChanges = true;
        }
        if(dto.getDate() != null && !dto.getDate().equals(slot.getDate())) {
            slot.setDate(dto.getDate());
            hasChanges = true;
        }
        if (dto.getStartTime() != null && !dto.getStartTime().equals(slot.getStartTime())) {
            slot.setStartTime(dto.getStartTime());
            hasChanges = true;
        }
        if (dto.getMaxMarks() != null && dto.getMaxMarks() != 0 && !dto.getMaxMarks().equals(slot.getMaxMarks())) {
            slot.setMaxMarks(dto.getMaxMarks());
            hasChanges = true;
        }
        if (dto.getSectionId() != null && !dto.getSectionId().equals(0L) && !dto.getSectionId().equals(slot.getSection().getId())) {
            Section section = sectionRepository.findById(dto.getSectionId())
                    .orElseThrow(() -> new ResourceNotFoundException("Section with id " + dto.getSectionId() + " not found"));
            slot.setSection(section);
            hasChanges = true;
        }
        if (dto.getSchoolClassId() != null && !dto.getPractical().equals(slot.getPractical())){
            SchoolClass schoolClass = schoolClassRepository.findById(dto.getSchoolClassId())
                    .orElseThrow(() -> new ResourceNotFoundException("SchoolClass with id " + dto.getSchoolClassId() + " not found"));
            slot.setSchoolClass(schoolClass);
            hasChanges = true;
        }
        if (dto.getDurationMinutes() != null && !dto.getDurationMinutes().equals(slot.getDurationMinutes())) {
            slot.setDurationMinutes(dto.getDurationMinutes());
            hasChanges = true;
        }
        if (hasChanges){
            slot.setUpdatedAt(Instant.now());
            slot.setUpdatedBy(updatedById);
            ExamSlot savedSlot = repo.save(slot);
            return mapper.toDto(savedSlot);
        }
        return mapper.toDto(slot);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        repo.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public ExamSlotResponseDto getById(Long id) {
        return mapper.toDto(repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("ExamSlot not found")));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ExamSlotResponseDto> list(Long examId, Pageable pageable) {
        Exam exam = examRepository.findById(examId)
                .orElseThrow(() -> new ResourceNotFoundException("Exam with id " + examId + " not found"));
        Page<ExamSlot> examSlots = repo.findByExam_Id(examId, pageable);
        return examSlots.map(mapper::toDto);
    }
}

