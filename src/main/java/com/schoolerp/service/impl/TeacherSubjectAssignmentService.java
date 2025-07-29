package com.schoolerp.service.impl;

import com.schoolerp.dto.request.TeacherSubjectAssignmentCreateDto;
import com.schoolerp.dto.response.TeacherSubjectAssignmentResponseDto;
import com.schoolerp.entity.SectionSubjectAssignment;
import com.schoolerp.entity.Teacher;
import com.schoolerp.entity.TeacherSubjectAssignment;
import com.schoolerp.exception.ResourceNotFoundException;
import com.schoolerp.exception.ValidationException;
import com.schoolerp.mapper.AssignmentMapper;
import com.schoolerp.repository.SectionSubjectAssignmentRepository;
import com.schoolerp.repository.TeacherRepository;
import com.schoolerp.repository.TeacherSubjectAssignmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
public class TeacherSubjectAssignmentService {

    @Autowired
    private TeacherRepository teacherRepo;
    @Autowired
    private SectionSubjectAssignmentRepository sectionSubjRepo;
    @Autowired
    private TeacherSubjectAssignmentRepository repo;
    @Autowired
    private AssignmentMapper mapper;

    @Transactional
    public void create(TeacherSubjectAssignmentCreateDto dto, Long teacherId) {
        Teacher teacher = teacherRepo.findById(teacherId).orElseThrow(() -> new ResourceNotFoundException("Teacher not found"));
        SectionSubjectAssignment sectionSubject = sectionSubjRepo.findById(dto.getSectionSubjectAssignmentId()).orElseThrow(() -> new ResourceNotFoundException("SectionSubjectAssignment not found"));

        boolean exists = repo.findBySectionSubjectAssignment_IdAndTeacher_Id(sectionSubject.getId(), teacher.getId()).isPresent();
        if (exists) throw new ValidationException("This subject in section is already assigned to this teacher");

        TeacherSubjectAssignment assignment = TeacherSubjectAssignment.builder()
                .teacher(teacher)
                .sectionSubjectAssignment(sectionSubject)
                .isClassTeacher(dto.getClassTeacher())
                .assignedDate(LocalDate.now())
                .build();

        repo.save(assignment);
    }

    public Page<TeacherSubjectAssignmentResponseDto> listByTeacher(Long teacherId, Pageable pageable) {
        return repo.findByTeacher_Id(teacherId, pageable).map(mapper::toDto);
    }
    public Page<TeacherSubjectAssignmentResponseDto> listBySectionSubjectAssignmentId
            (Long sectionSubjectAssignmentId, Pageable pageable) {
        return repo.findBySectionSubjectAssignment_Id(sectionSubjectAssignmentId, pageable).map(mapper::toDto);
    }

    public void delete(Long id) {
        repo.deleteById(id);
    }
}
