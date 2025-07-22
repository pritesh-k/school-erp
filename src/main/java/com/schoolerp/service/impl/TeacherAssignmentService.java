package com.schoolerp.service.impl;

import com.schoolerp.dto.request.SectionSummaryDto;
import com.schoolerp.dto.request.SubjectSummaryDto;
import com.schoolerp.dto.request.TeacherAssignmentDto;
import com.schoolerp.dto.response.SectionResponseDto;
import com.schoolerp.dto.response.SubjectResponseDto;
import com.schoolerp.dto.response.TeachersAssignedDto;
import com.schoolerp.entity.Section;
import com.schoolerp.entity.Subject;
import com.schoolerp.entity.Teacher;
import com.schoolerp.exception.ResourceNotFoundException;
import com.schoolerp.mapper.SectionMapper;
import com.schoolerp.mapper.SubjectMapper;
import com.schoolerp.mapper.TeacherMapper;
import com.schoolerp.repository.SectionRepository;
import com.schoolerp.repository.SubjectRepository;
import com.schoolerp.repository.TeacherRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class TeacherAssignmentService {

    private final TeacherRepository teacherRepo;
    private final SectionRepository sectionRepo;
    private final SubjectRepository subjectRepo;
    private final TeacherMapper mapper;
    private final SubjectMapper subjectMapper;
    private final SectionMapper sectionMapper;


    public void assignSection(Long teacherId, Long sectionId, Long userId) {
        Teacher teacher = getTeacherById(teacherId);
        Section section = getSectionById(sectionId);

        // Check if section already has a class teacher
        if (section.getAssignedTeachers() != null && section.getAssignedTeachers().contains(teacher)) {
            throw new IllegalStateException("Section already has this teacher assigned: " +
                    teacher.getDisplayName());
        }

        // Assign teacher to section
        section.getAssignedTeachers().add(teacher);
        section.setUpdatedAt(java.time.Instant.now());
        sectionRepo.save(section);

        log.info("Assigned teacher {} to section {}", teacherId, sectionId);
    }

    public void assignClassTeacherToSection(Long teacherId, Long sectionId, Long userId) {
        Teacher teacher = getTeacherById(teacherId);
        Section section = getSectionById(sectionId);

        // Check if section already has a class teacher
        if (section.getClassTeacherId() != null && section.getClassTeacherId().equals(teacherId)) {
            throw new IllegalStateException("Section already has this teacher assigned: " +
                    teacher.getDisplayName());
        }

        // Assign class teacher to section
        section.setClassTeacherId(teacherId);
        section.getAssignedTeachers().add(teacher);
        section.setUpdatedAt(java.time.Instant.now());
        sectionRepo.save(section);

        log.info("Assigned teacher {} to section {}", teacherId, sectionId);
    }

    public void updateClassTeacherToSection(Long teacherId, Long sectionId, Long userId) {
        Teacher teacher = getTeacherById(teacherId);
        Section section = getSectionById(sectionId);

        // Check if section already has a class teacher
        if (section.getClassTeacherId() != null && section.getClassTeacherId().equals(teacherId)) {
            throw new IllegalStateException("Section already has this teacher assigned: " +
                    teacher.getDisplayName());
        }

        // Assign class teacher to section
        section.setClassTeacherId(teacherId);
        section.getAssignedTeachers().add(teacher);
        section.setUpdatedAt(java.time.Instant.now());

        sectionRepo.save(section);

        log.info("Assigned teacher {} to section {}", teacherId, sectionId);
    }

    public void removeTeacherFromSection(Long teacherId, Long sectionId, Long userId) {
        Teacher teacher = getTeacherById(teacherId);
        Section section = getSectionById(sectionId);

        // Verify teacher is assigned to this section
        if (section.getAssignedTeachers() == null || !section.getAssignedTeachers().contains(teacher)) {
            throw new IllegalStateException("Teacher "+ teacher.getDisplayName() +" is not assigned to this section");
        }

        // Remove assignment
        section.getAssignedTeachers().remove(teacher);
        section.setUpdatedAt(java.time.Instant.now());

        sectionRepo.save(section);

        log.info("Removed teacher {} from section {}", teacherId, sectionId);
    }

    public void assignSubject(Long teacherId, Long subjectId, Long userId) {
        Teacher teacher = getTeacherById(teacherId);
        Subject subject = getSubjectById(subjectId);

        // Check if already assigned
        if (subject.getTeachersAssigned().contains(teacher)) {
            throw new IllegalStateException("Teacher is already assigned to this subject: " + subject.getCode());
        }

        // Add teacher to subject's assigned teachers
        subject.getTeachersAssigned().add(teacher);
        subject.setUpdatedAt(java.time.Instant.now());
        subjectRepo.save(subject);

        log.info("Assigned teacher {} to subject {}", teacherId, subject.getCode());
    }

    public void removeSubject(Long teacherId, Long subjectId, Long userId) {
        Teacher teacher = getTeacherById(teacherId);
        Subject subject = getSubjectById(subjectId);

        // Check if assigned
        if (subject.getTeachersAssigned() == null || !subject.getTeachersAssigned().contains(teacher)) {
            throw new IllegalStateException("Teacher is not assigned to this subject: " + subject.getCode());
        }

        // Remove teacher from subject
        subject.getTeachersAssigned().remove(teacher);
        subject.setUpdatedAt(java.time.Instant.now());

        subjectRepo.save(subject);

        log.info("Removed teacher {} from subject {}", teacherId, subject.getCode());
    }

    @Transactional(readOnly = true)
    public TeacherAssignmentDto getTeacherAssignments(Long teacherId, boolean subectsOnly, boolean sectionsOnly) {
        Teacher teacher = getTeacherById(teacherId);

        List<Subject> subjects = new ArrayList<>();
        List<Section> sections = new ArrayList<>();
        TeacherAssignmentDto assignmentDto = null;
        // Get subjects where this teacher is assigned
        if(subectsOnly) {
            subjects = subjectRepo.findByTeachersAssignedIdAndDeletedFalse(teacherId);
            assignmentDto = new TeacherAssignmentDto(
                    teacher.getId(),
                    teacher.getDisplayName(),
                    teacher.getEmployeeCode(),
                    new ArrayList<>(), // Assigned sections will be populated later
                    subjects.stream()
                            .map(subjectMapper::toSubjectSummary)
                            .collect(Collectors.toList())
            );
        } else if (sectionsOnly) { // Get sections where this teacher is class teacher
            sections = sectionRepo.findByClassTeacherIdAndDeletedFalse(teacherId);
            assignmentDto = new TeacherAssignmentDto(
                    teacher.getId(),
                    teacher.getDisplayName(),
                    teacher.getEmployeeCode(),
                    sections.stream()
                            .map(sectionMapper::toSectionSummary)
                            .collect(Collectors.toList()),
                    new ArrayList<>()
            );
        }
        return assignmentDto;
    }

    // Helper methods
    private Teacher getTeacherById(Long teacherId) {
        return teacherRepo.findById(teacherId)
                .orElseThrow(() -> new ResourceNotFoundException("Teacher not found with ID: " + teacherId));
    }

    private Section getSectionById(Long sectionId) {
        return sectionRepo.findById(sectionId)
                .orElseThrow(() -> new ResourceNotFoundException("Section not found with ID: " + sectionId));
    }

    private Subject getSubjectById(Long subjectId) {
        return subjectRepo.findById(subjectId)
                .orElseThrow(() -> new ResourceNotFoundException("Subject not found with ID: " + subjectId));
    }

    public Page<TeachersAssignedDto> getAssignedTeachers(Long subjectId, Pageable pageable) {
        Page<Teacher> pagedTeachers = subjectRepo.findTeachersBySubjectId(subjectId, pageable);
        return pagedTeachers.map(mapper::toAssignedDto);
    }

    public Page<Subject> getSubjectsByTeacherId(Long teacherId, Pageable pageable) {
        return subjectRepo.findSubjectsByTeacherId(teacherId, pageable);
    }

    public Page<TeachersAssignedDto> getTeachersBySectionId(Long sectionId, Pageable pageable) {
        Page<Teacher> sectionTeachers = subjectRepo.findSubjectTeachersBySectionId(sectionId, pageable);
        return sectionTeachers.map(mapper::toAssignedDto);
    }

    public Page<TeachersAssignedDto> getTeachersBySubjectId(Long subjectId, Pageable pageable) {
        Page<Teacher> subjectTeachers = subjectRepo.findTeachersBySubjectId(subjectId, pageable);
        return subjectTeachers.map(mapper::toAssignedDto);
    }

    public Page<SectionResponseDto> findSectionsByClassTeacherId(Long teacherId, Pageable pageable) {
        Page<Section> page = sectionRepo.findSectionsByClassTeacherId(teacherId, pageable);
        return page.map(sectionMapper::toDto);
    }

    public Page<SectionResponseDto> findSectionsByTeacherId(Long teacherId, Pageable pageable) {
        Page<Section> page = sectionRepo.findSectionsByClassTeacherId(teacherId, pageable);
        return page.map(sectionMapper::toDto);
    }

}
