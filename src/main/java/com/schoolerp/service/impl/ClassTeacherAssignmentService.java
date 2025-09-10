package com.schoolerp.service.impl;

import com.schoolerp.dto.request.ClassTeacherAssignmentRequest;
import com.schoolerp.dto.response.ClassTeacherAssignmentResponse;
import com.schoolerp.entity.AcademicSession;
import com.schoolerp.entity.ClassTeacherAssignment;
import com.schoolerp.entity.Section;
import com.schoolerp.entity.Teacher;
import com.schoolerp.exception.ResourceNotFoundException;
import com.schoolerp.mapper.ClassTeacherMapper;
import com.schoolerp.repository.AcademicSessionRepository;
import com.schoolerp.repository.ClassTeacherAssignmentRepository;
import com.schoolerp.repository.SectionRepository;
import com.schoolerp.repository.TeacherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class ClassTeacherAssignmentService {

    private final ClassTeacherAssignmentRepository repository;

    private final AcademicSessionRepository academicSessionRepository;

    private final TeacherRepository teacherRepository;

    private final SectionRepository sectionRepository;

    private final ClassTeacherMapper mapper;
    public ClassTeacherAssignmentResponse assignClassTeacher(ClassTeacherAssignmentRequest request,
                                                             String sessionName, Long createdByUserId) {
        AcademicSession session = academicSessionRepository.findByName(sessionName)
                .orElseThrow(() -> new IllegalArgumentException("Academic session not found: " + sessionName));
        Section section = sectionRepository.findById(request.getSectionId())
                .orElseThrow(() -> new IllegalArgumentException("Section not found with ID: " + request.getSectionId()));
        if (repository.existsBySection_IdAndAcademicSession_Id(session.getId(), section.getId())) {
            throw new IllegalStateException("A class teacher is already assigned for this section in this session.");
        }

        Teacher teacher = teacherRepository.findById(request.getTeacherId())
                .orElseThrow(() -> new IllegalArgumentException("Teacher not found with ID: " + request.getTeacherId()));

        ClassTeacherAssignment assignment = ClassTeacherAssignment.builder()
                .teacher(teacher)
                .section(section)
                .academicSession(session)
                .build();

        assignment.setCreatedAt(Instant.now());
        assignment.setCreatedBy(createdByUserId);
        assignment.setActive(true);
        assignment.setDeleted(false);

        return mapper.toDto(repository.save(assignment));
    }

    public ClassTeacherAssignmentResponse getClassTeacherBySection(Long sectionId, String academicSessionName) {
        AcademicSession session = academicSessionRepository.findByName(academicSessionName)
                .orElseThrow(() -> new ResourceNotFoundException("Academic session not found: " + academicSessionName));
        ClassTeacherAssignment assignment = repository.findBySection_IdAndAcademicSession_Id(sectionId, session.getId()).
                orElseThrow(() -> new ResourceNotFoundException("No class teacher assigned for this section in this session."));
        return mapper.toDto(assignment);
    }

    public Page<ClassTeacherAssignmentResponse> getTeacherAssignments(Pageable pageable, Long teacherId, String academicSessionName) {
        AcademicSession session = academicSessionRepository.findByName(academicSessionName)
                .orElseThrow(() -> new ResourceNotFoundException("Academic session not found: " + academicSessionName));
        Page<ClassTeacherAssignment> assignmentPage;
        if (teacherId != null) {
            assignmentPage = repository.findByTeacher_IdAndAcademicSession_Id(pageable, teacherId, session.getId());
        } else {
            assignmentPage = repository.findByAcademicSession_Id(pageable, session.getId());
        }
        return assignmentPage.map(mapper::toDto);
    }

    public void removeAssignment(Long assignmentId) {
        if (!repository.existsById(assignmentId)) {
            throw new ResourceNotFoundException("Class teacher assignment not found with ID: " + assignmentId);
        }
        repository.deleteById(assignmentId);
    }
}

