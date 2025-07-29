package com.schoolerp.service.impl;

import com.schoolerp.dto.request.AssignmentRequest;
import com.schoolerp.dto.request.ClassTeacherAssignmentDto;
import com.schoolerp.dto.request.SectionTeacherAssignmentListDto;
import com.schoolerp.dto.response.ApiResponse;
import com.schoolerp.dto.response.AssignmentResponse;
import com.schoolerp.dto.response.TeachingSectionDto;
import com.schoolerp.entity.SchoolClass;
import com.schoolerp.entity.Section;
import com.schoolerp.entity.SectionTeacherAssignment;
import com.schoolerp.entity.Teacher;
import com.schoolerp.exception.ResourceNotFoundException;
import com.schoolerp.exception.ValidationException;
import com.schoolerp.mapper.SectionTeacherAssignmentMapper;
import com.schoolerp.repository.SchoolClassRepository;
import com.schoolerp.repository.SectionRepository;
import com.schoolerp.repository.SectionTeacherAssignmentRepository;
import com.schoolerp.repository.TeacherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class SectionTeacherAssignmentService {

    private final SectionTeacherAssignmentRepository assignmentRepo;
    private final TeacherRepository teacherRepo;
    private final SectionRepository sectionRepo;
    private final SectionTeacherAssignmentMapper mapper;

    private final SchoolClassRepository schoolClassRepo;

    public AssignmentResponse assignTeacher(AssignmentRequest request, Long classId, Long sectionId) {
        Teacher teacher = teacherRepo.findById(request.getTeacherId())
                .orElseThrow(() -> new ResourceNotFoundException("Teacher not found"));
        SchoolClass schoolClass = schoolClassRepo.findById(classId)
                .orElseThrow(() -> new ResourceNotFoundException("Class not found"));

        Section section = sectionRepo.findById(sectionId)
                .orElseThrow(() -> new ResourceNotFoundException("Section not found"));

        // Check if the section belongs to the class
        if (!section.getSchoolClass().getId().equals(schoolClass.getId())) {
            throw new ValidationException("Section does not belong to the selected class");
        }

        SectionTeacherAssignment assignment = SectionTeacherAssignment.builder()
                .teacher(teacher)
                .section(section)
                .assignedDate(LocalDate.now())
                .isClassTeacher(Boolean.TRUE.equals(request.getClassTeacher()))
                .teacherName(teacher.getDisplayName())
                .build();

        assignmentRepo.save(assignment);
        return mapper.toDto(assignment);
    }

    public Page<SectionTeacherAssignmentListDto> listAll(Pageable pageable) {
        Page<SectionTeacherAssignment> list = assignmentRepo.findAll(pageable);
        Page<SectionTeacherAssignmentListDto> responsePage = list.map(mapper::onlyData);
        return responsePage;
    }

    public AssignmentResponse getById(Long assignmentId) {
        SectionTeacherAssignment assignment = assignmentRepo.findById(assignmentId)
                .orElseThrow(() -> new ResourceNotFoundException("Assignment not found"));
        return mapper.toDto(assignment);
    }

    public Page<AssignmentResponse> getByTeacherId(Long teacherId, Pageable pageable) {
        Page<SectionTeacherAssignment> assignments = assignmentRepo.findByTeacher_Id(teacherId, pageable);
        if (assignments.isEmpty()) {
            throw new ResourceNotFoundException("No assignments found for this teacher");
        }
        return assignments.map(mapper::toDto);
    }

    public Page<TeachingSectionDto> getTeachingSections(Long teacherId, Pageable pageable) {
        return assignmentRepo.findTeachingSectionsByTeacherId(teacherId, pageable);
    }


    public AssignmentResponse update(Long id, AssignmentRequest request) {
        SectionTeacherAssignment assignment = assignmentRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Assignment not found"));

        assignment.setClassTeacher(request.getClassTeacher());
        assignment.setAssignedDate(LocalDate.now());

        assignmentRepo.save(assignment);
        return mapper.toDto(assignment);
    }

    public ApiResponse<Void> delete(Long id) {
        assignmentRepo.deleteById(id);
        return ApiResponse.ok(null);
    }

    public Page<AssignmentResponse> getBySection(Long sectionId, Pageable pageable) {
        return assignmentRepo.findBySection_Id(sectionId, pageable).map(mapper::toDto);
    }

    public void assignClassTeacher(ClassTeacherAssignmentDto dto, Long userId) {
        Section section = sectionRepo.findById(dto.getSectionId())
                .orElseThrow(() -> new ResourceNotFoundException("Section not found"));
        Teacher teacher = teacherRepo.findById(dto.getTeacherId())
                .orElseThrow(() -> new ResourceNotFoundException("Teacher not found"));

        // Check if assignment exists
        Optional<SectionTeacherAssignment> existing = assignmentRepo.findByTeacherIdAndSectionId(dto.getTeacherId(), dto.getSectionId());
        if (existing.isPresent()) {
            existing.get().setClassTeacher(true);
            existing.get().setUpdatedBy(userId);
            existing.get().setUpdatedAt(Instant.now());
            assignmentRepo.save(existing.get());
        } else {
            SectionTeacherAssignment assignment = SectionTeacherAssignment.builder()
                    .teacher(teacher)
                    .section(section)
                    .isClassTeacher(true)
                    .assignedDate(LocalDate.now())
                    .build();
            assignment.setCreatedBy(userId);
            assignment.setCreatedAt(Instant.now());
            assignmentRepo.save(assignment);
        }
    }

}
