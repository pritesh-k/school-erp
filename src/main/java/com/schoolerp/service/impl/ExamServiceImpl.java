package com.schoolerp.service.impl;

import com.schoolerp.dto.request.ExamCreateDto;
import com.schoolerp.dto.request.ExamUpdateDto;
import com.schoolerp.dto.response.ExamResponseDto;
import com.schoolerp.entity.AcademicSession;
import com.schoolerp.entity.Exam;
import com.schoolerp.entity.SchoolClass;
import com.schoolerp.entity.Subject;
import com.schoolerp.enums.ExamStatus;
import com.schoolerp.exception.ResourceNotFoundException;
import com.schoolerp.mapper.ExamMapper;
import com.schoolerp.repository.AcademicSessionRepository;
import com.schoolerp.repository.ExamRepository;
import com.schoolerp.repository.SchoolClassRepository;
import com.schoolerp.repository.SubjectRepository;
import com.schoolerp.service.ExamService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ExamServiceImpl implements ExamService {

    private final ExamRepository repo;
    private final ExamMapper mapper;
    private final SubjectRepository subjectRepo;

    private final AcademicSessionRepository academicSessionRepository;

    @Override
    @Transactional
    public ExamResponseDto create(ExamCreateDto dto, Long userId, Long entityId, String role, String academicSession) {
        validateExamInput(dto);

        AcademicSession academicSession1 = academicSessionRepository.findByName(academicSession)
                .orElseThrow(() -> new ResourceNotFoundException("Academic session not found: " + academicSession));

        Exam exam = Exam.builder()
                .name(dto.getName())
                .term(dto.getTerm())
                .startDate(dto.getStartDate())
                .endDate(dto.getEndDate())
                .academicSession(academicSession1)
                .status(ExamStatus.DRAFT)
                .build();
        exam.setActive(true);
        exam.setDeleted(false);
        exam.setCreatedAt(java.time.Instant.now());
        exam.setCreatedBy(userId);

        Exam savedExam = repo.save(exam);

        return mapper.toDto(savedExam);
    }


    @Override
    public ExamResponseDto get(Long id, Long userId, Long entityId, String role) {
        Exam exam = repo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Exam not found with id: " + id));
        return mapper.toDto(exam);
    }

    @Override
    public Page<ExamResponseDto> list(Pageable pageable, Long userId, Long entityId, String academicSession) {
        AcademicSession academicSession1 = academicSessionRepository.findByName(academicSession)
                .orElseThrow(() -> new ResourceNotFoundException("Academic session not found: " + academicSession));

        Page<Exam> examsPage = repo.findByAcademicSession_Id(academicSession1.getId(), pageable);
        return examsPage.map(mapper::toDto);
    }

    @Override
    @Transactional
    public ExamResponseDto update(Long id, ExamUpdateDto dto, Long userId, Long entityId, String role) {
        Exam e = repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Exam with id " + id + " not found"));
        boolean hasChanges = false;
        // Update name
        if (dto.getName() != null && !dto.getName().isBlank() && !dto.getName().equals(e.getName())) {
            e.setName(dto.getName().trim());
            hasChanges = true;
        }

        // Update term
        if (dto.getTerm() != null && !dto.getTerm().equals(e.getTerm())) {
            e.setTerm(dto.getTerm());
            hasChanges = true;
        }

        // Update dates with validation
        if (dto.getStartDate() != null) {
            if (dto.getEndDate() != null && dto.getStartDate().isAfter(dto.getEndDate())) {
                throw new IllegalArgumentException("Start date cannot be after end date");
            }
            if (dto.getStartDate().isBefore(LocalDate.now())) {
                throw new IllegalArgumentException("Start date cannot be in the past");
            }
            e.setStartDate(dto.getStartDate());
            hasChanges = true;
        }

        if (dto.getEndDate() != null) {
            if (e.getStartDate() != null && dto.getEndDate().isBefore(e.getStartDate())) {
                throw new IllegalArgumentException("End date cannot be before start date");
            }
            e.setEndDate(dto.getEndDate());
            hasChanges = true;
        }

        if (hasChanges){
            e.setUpdatedAt(java.time.Instant.now());
            e.setUpdatedBy(userId);
        } else {
            // No changes to save
            return mapper.toDto(e);
        }
        return mapper.toDto(repo.save(e));
    }

    @Override
    @Transactional
    public ExamResponseDto publishExam(Long id, Long publishedById){
        Exam e = repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Exam with id " + id + " not found"));
        if (e.getStatus() == ExamStatus.PUBLISHED) {
            throw new IllegalArgumentException("Exam with id " + id + " is already published");
        }
        e.setStatus(ExamStatus.PUBLISHED);
        e.setPublishedBy(publishedById);
        Exam saved = repo.save(e);
        return mapper.toDto(saved);
    }
    @Override
    @Transactional
    public void delete(Long id, Long userId, Long entityId, String role) {
        if (!repo.existsById(id)) {
            throw new ResourceNotFoundException("Exam with id " + id + " not found");
        }
        repo.deleteById(id);
    }

    private void validateExamInput(ExamCreateDto dto) {
        if (dto == null) {
            throw new IllegalArgumentException("Exam data cannot be null");
        }

        // Validate dates
        if (dto.getStartDate().isAfter(dto.getEndDate())) {
            throw new IllegalArgumentException("Start date cannot be after end date");
        }

        if (dto.getStartDate().isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("Start date cannot be in the past");
        }

        // Validate exam duration (reasonable limits)
        long daysBetween = ChronoUnit.DAYS.between(dto.getStartDate(), dto.getEndDate());
        if (daysBetween > 30) {
            throw new IllegalArgumentException("Exam duration cannot exceed 30 days");
        }
    }
}