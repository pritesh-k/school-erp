package com.schoolerp.service.impl;

import com.schoolerp.dto.request.SubjectAssignmentCreateDto;
import com.schoolerp.dto.request.SubjectAssignmentUpdateDto;
import com.schoolerp.dto.response.SubjectAssignmentResponseDto;
import com.schoolerp.entity.Section;
import com.schoolerp.entity.Subject;
import com.schoolerp.entity.SubjectAssignment;
import com.schoolerp.entity.Teacher;
import com.schoolerp.exception.ResourceNotFoundException;
import com.schoolerp.exception.ValidationException;
import com.schoolerp.mapper.SubjectAssignmentMapper;
import com.schoolerp.repository.SectionRepository;
import com.schoolerp.repository.SubjectAssignmentRepository;
import com.schoolerp.repository.SubjectRepository;
import com.schoolerp.repository.TeacherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class SubjectAssignmentService {

    private final SubjectAssignmentRepository repo;
    private final TeacherRepository teacherRepo;
    private final SubjectRepository subjectRepo;
    private final SectionRepository sectionRepo;
    private final SubjectAssignmentMapper mapper;

    public SubjectAssignmentResponseDto create(SubjectAssignmentCreateDto dto, Long createdByUserId) {
        if (repo.findByTeacherIdAndSubjectIdAndSectionId(dto.getTeacherId(), dto.getSubjectId(), dto.getSectionId()).isPresent()) {
            throw new ValidationException("This subject is already assigned to this teacher in this section.");
        }

        Teacher teacher = teacherRepo.findById(dto.getTeacherId())
                .orElseThrow(() -> new ResourceNotFoundException("Teacher not found"));
        Subject subject = subjectRepo.findById(dto.getSubjectId())
                .orElseThrow(() -> new ResourceNotFoundException("Subject not found"));
        Section section = sectionRepo.findById(dto.getSectionId())
                .orElseThrow(() -> new ResourceNotFoundException("Section not found"));

        SubjectAssignment assignment = SubjectAssignment.builder()
                .teacher(teacher)
                .subject(subject)
                .section(section)
                .assignedDate(LocalDate.now())
                .build();
        assignment.setCreatedBy(createdByUserId);
        assignment.setCreatedAt(Instant.now());
        assignment.setActive(true);
        assignment.setDeleted(false);

        repo.save(assignment);
        return mapper.toDto(assignment);
    }

    public SubjectAssignmentResponseDto update(SubjectAssignmentUpdateDto dto, Long updatedByUserId) {
        SubjectAssignment assignment = repo.findById(dto.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Assignment not found"));

        if (!assignment.getTeacher().getId().equals(dto.getTeacherId())) {
            Teacher newTeacher = teacherRepo.findById(dto.getTeacherId())
                    .orElseThrow(() -> new ResourceNotFoundException("Teacher not found"));

            boolean duplicate = repo.findByTeacherIdAndSubjectIdAndSectionId(dto.getTeacherId(),
                    assignment.getSubject().getId(), assignment.getSection().getId()).isPresent();

            if (duplicate) {
                throw new ValidationException("This assignment already exists with another teacher.");
            }

            assignment.setTeacher(newTeacher);
        }

        assignment.setUpdatedAt(Instant.now());
        assignment.setUpdatedBy(updatedByUserId);

        return mapper.toDto(repo.save(assignment));
    }

    public void delete(Long id) {
        SubjectAssignment assignment = repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Assignment not found"));

        repo.delete(assignment);
    }

    public Page<SubjectAssignmentResponseDto> list(Pageable pageable) {
        return repo.findAll(pageable)
                .map(mapper::toDto);
    }

    public Page<SubjectAssignmentResponseDto> listByTeacher(Long teacherId, Pageable pageable) {
        return repo.findByTeacher_Id(teacherId, pageable)
                .map(mapper::toDto);
    }

    public Page<SubjectAssignmentResponseDto> listBySection(Long sectionId, Pageable pageable) {
        return repo.findBySection_Id(sectionId, pageable).map(mapper::toDto);
    }

    public Page<SubjectAssignmentResponseDto> listBySubject(Long subjectId, Pageable pageable) {
        return repo.findBySubject_Id(subjectId, pageable).map(mapper::toDto);
    }
}

