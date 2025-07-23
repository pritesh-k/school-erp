package com.schoolerp.service.impl;

import com.schoolerp.dto.request.ExamCreateDto;
import com.schoolerp.dto.response.ExamResponseDto;
import com.schoolerp.entity.Exam;
import com.schoolerp.entity.SchoolClass;
import com.schoolerp.entity.Subject;
import com.schoolerp.exception.ResourceNotFoundException;
import com.schoolerp.mapper.ExamMapper;
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
    private final SchoolClassRepository classRepo;
    private final SubjectRepository subjectRepo;

    @Override
    @Transactional
    public ExamResponseDto create(ExamCreateDto dto, Long userId, Long entityId, String role) {
        log.info("Creating exam: {} for class ID: {}", dto.getName(), dto.getSchoolClassId());

        // Validate input
        validateExamInput(dto);

        // Validate and fetch school class
        SchoolClass schoolClass = validateAndGetSchoolClass(dto.getSchoolClassId());

        // Validate and fetch subjects
        Set<Subject> subjects = validateAndGetSubjects(dto.getSubjectIds(), schoolClass);

        // Check for existing exam conflicts
        validateExamConflicts(dto, schoolClass.getId());

        // Create exam
        Exam exam = createExam(dto, schoolClass, subjects, userId, entityId, role);

        Exam savedExam = repo.save(exam);
        log.info("Exam created successfully with ID: {} for class: {}",
                savedExam.getId(), schoolClass.getName());

        return mapper.toDto(savedExam);
    }


    @Override
    public ExamResponseDto get(Long id, Long userId, Long entityId, String role) {
        return mapper.toDto(repo.findById(id).orElseThrow());
    }

    @Override
    public Page<ExamResponseDto> list(Pageable pageable, Long userId, Long entityId, String role) {
        return repo.findAll(pageable).map(mapper::toDto);
    }

    @Override
    @Transactional
    public ExamResponseDto update(Long id, ExamCreateDto dto, Long userId, Long entityId, String role) {
        Exam e = repo.findById(id).orElseThrow();
        e.setName(dto.getName());
        e.setTerm(dto.getTerm());
        e.setStartDate(dto.getStartDate());
        e.setEndDate(dto.getEndDate());
        e.setSchoolClass(classRepo.getReferenceById(dto.getSchoolClassId()));
        return mapper.toDto(repo.save(e));
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

        // Validate subject count
        if (dto.getSubjectIds().size() > 20) {
            throw new IllegalArgumentException("Cannot have more than 20 subjects in an exam");
        }
    }

    private SchoolClass validateAndGetSchoolClass(Long schoolClassId) {
        return classRepo.findById(schoolClassId)
                .orElseThrow(() -> new ResourceNotFoundException("School class not found with ID: " + schoolClassId));
    }

    private Set<Subject> validateAndGetSubjects(Set<Long> subjectIds, SchoolClass schoolClass) {
        // Fetch all subjects at once
        List<Subject> subjects = subjectRepo.findAllById(subjectIds);

        // Check if all subjects exist
        if (subjects.size() != subjectIds.size()) {
            Set<Long> foundIds = subjects.stream().map(Subject::getId).collect(Collectors.toSet());
            Set<Long> missingIds = subjectIds.stream()
                    .filter(id -> !foundIds.contains(id))
                    .collect(Collectors.toSet());

            throw new ResourceNotFoundException("Subjects not found with IDs: " + missingIds);
        }

        // Validate subjects belong to the same class (if needed)
        // Uncomment if subjects are class-specific
    /*
    List<Subject> invalidSubjects = subjects.stream()
        .filter(subject -> !subject.getSchoolClasses().contains(schoolClass))
        .collect(Collectors.toList());

    if (!invalidSubjects.isEmpty()) {
        throw new IllegalArgumentException("Some subjects don't belong to the specified class: " +
            invalidSubjects.stream().map(Subject::getName).collect(Collectors.joining(", ")));
    }
    */

        return new HashSet<>(subjects);
    }

    private void validateExamConflicts(ExamCreateDto dto, Long schoolClassId) {
        // Check for overlapping exams in the same class
        List<Exam> conflictingExams = repo.findBySchoolClassIdAndDateRange(
                schoolClassId, dto.getStartDate(), dto.getEndDate());

        if (!conflictingExams.isEmpty()) {
            String conflictNames = conflictingExams.stream()
                    .map(Exam::getName)
                    .collect(Collectors.joining(", "));

            throw new IllegalArgumentException(
                    "Exam dates conflict with existing exams: " + conflictNames);
        }

        // Check for same term exam in the class
        if (repo.existsBySchoolClassIdAndTermAndNameNot(schoolClassId, dto.getTerm(), dto.getName())) {
            throw new IllegalArgumentException(
                    "An exam for term " + dto.getTerm() + " already exists in this class");
        }
    }

    private Exam createExam(ExamCreateDto dto, SchoolClass schoolClass, Set<Subject> subjects, Long userId, Long entityId, String role) {
        Exam exam = Exam.builder()
                .name(dto.getName())
                .term(dto.getTerm())
                .startDate(dto.getStartDate())
                .endDate(dto.getEndDate())
                .schoolClass(schoolClass)
                .subjects(subjects)
                .examResults(new HashSet<>())
                .build();
        exam.setActive(true);  // Set via setter
        exam.setDeleted(false);
        exam.setCreatedAt(java.time.Instant.now());
        exam.setCreatedBy(userId);
        return exam;
    }

}