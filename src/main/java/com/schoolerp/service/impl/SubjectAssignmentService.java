package com.schoolerp.service.impl;

import com.schoolerp.dto.request.SubjectAssignmentCreateDto;
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
        Teacher teacher = teacherRepo.findById(dto.getTeacherId())
                .orElseThrow(() -> new ResourceNotFoundException("Teacher not found"));
        Subject subject = subjectRepo.findById(dto.getSubjectId())
                .orElseThrow(() -> new ResourceNotFoundException("Subject not found"));
        Section section = sectionRepo.findById(dto.getSectionId())
                .orElseThrow(() -> new ResourceNotFoundException("Section not found"));

        boolean exists = repo.findByTeacherIdAndSubjectIdAndSectionId(
                dto.getTeacherId(), dto.getSubjectId(), dto.getSectionId()).isPresent();
        if (exists)
            throw new ValidationException("This subject is already assigned to this teacher in the section");

        SubjectAssignment assignment = SubjectAssignment.builder()
                .teacher(teacher)
                .subject(subject)
                .section(section)
                .assignedDate(LocalDate.now())
                .build();

        repo.save(assignment);
        return mapper.toDto(assignment);
    }

    public Page<SubjectAssignmentResponseDto> list(Pageable pageable) {
        return repo.findAll(pageable)
                .map(mapper::toDto);
    }

    public void delete(Long id) {
        if (!repo.existsById(id))
            throw new ResourceNotFoundException("Assignment not found");
        repo.deleteById(id);
    }
}

