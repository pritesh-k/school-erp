package com.schoolerp.service.impl;

import com.schoolerp.dto.request.AssignmentRequest;
import com.schoolerp.dto.response.ApiResponse;
import com.schoolerp.dto.response.AssignmentResponse;
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

import java.time.LocalDate;
import java.util.List;

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
                .build();

        assignmentRepo.save(assignment);
        return mapper.toDto(assignment);
    }

    public Page<AssignmentResponse> listAll(Pageable pageable) {
        Page<SectionTeacherAssignment> list = assignmentRepo.findAll(pageable);
        Page<AssignmentResponse> responsePage = list.map(mapper::toDto);
        return responsePage;
    }

    public ApiResponse<AssignmentResponse> getById(Long id) {
        SectionTeacherAssignment assignment = assignmentRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Assignment not found"));
        return ApiResponse.ok(mapper.toDto(assignment));
    }

    public ApiResponse<AssignmentResponse> update(Long id, AssignmentRequest request) {
        SectionTeacherAssignment assignment = assignmentRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Assignment not found"));

        assignment.setClassTeacher(request.getClassTeacher());
        assignment.setAssignedDate(LocalDate.now());

        assignmentRepo.save(assignment);
        return ApiResponse.ok(mapper.toDto(assignment));
    }

    public ApiResponse<Void> delete(Long id) {
        assignmentRepo.deleteById(id);
        return ApiResponse.ok(null);
    }
}
