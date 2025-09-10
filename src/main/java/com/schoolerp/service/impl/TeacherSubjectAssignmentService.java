package com.schoolerp.service.impl;

import com.schoolerp.dto.request.ClassTeacherAssignmentRequest;
import com.schoolerp.dto.request.TeacherSubjectAssignmentCreateDto;
import com.schoolerp.dto.response.TeacherSubjectAssignmentResponseDto;
import com.schoolerp.entity.AcademicSession;
import com.schoolerp.entity.SectionSubjectAssignment;
import com.schoolerp.entity.Teacher;
import com.schoolerp.entity.TeacherSubjectAssignment;
import com.schoolerp.exception.ResourceNotFoundException;
import com.schoolerp.exception.ValidationException;
import com.schoolerp.mapper.AssignmentMapper;
import com.schoolerp.repository.AcademicSessionRepository;
import com.schoolerp.repository.SectionSubjectAssignmentRepository;
import com.schoolerp.repository.TeacherRepository;
import com.schoolerp.repository.TeacherSubjectAssignmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class TeacherSubjectAssignmentService {

    @Autowired
    private TeacherRepository teacherRepo;
    @Autowired
    private SectionSubjectAssignmentRepository sectionSubjRepo;
    @Autowired
    private TeacherSubjectAssignmentRepository repo;

    @Autowired
    private AcademicSessionRepository academicSessionRepository;

    @Autowired
    private ClassTeacherAssignmentService classTeacherAssignmentService;
    @Autowired
    private AssignmentMapper mapper;

    @Transactional
    public void create(TeacherSubjectAssignmentCreateDto dto, Long teacherId, String academicSessionName, Long createdById) {
        Teacher teacher = teacherRepo.findById(teacherId).orElseThrow(() -> new ResourceNotFoundException("Teacher not found"));
        SectionSubjectAssignment sectionSubject = sectionSubjRepo.findById(dto.getSectionSubjectAssignmentId()).orElseThrow(() -> new ResourceNotFoundException("SectionSubjectAssignment not found"));

        Optional<AcademicSession> academicSession = academicSessionRepository.findByName(academicSessionName);
        if (academicSession.isEmpty()){
            throw new ResourceNotFoundException("Academic Session is not present");
        }

        boolean exists = repo.findBySectionSubjectAssignment_IdAndTeacher_Id(sectionSubject.getId(), teacher.getId()).isPresent();
        if (exists) throw new ValidationException("This subject in section is already assigned to this teacher");

        TeacherSubjectAssignment assignment = TeacherSubjectAssignment.builder()
                .teacher(teacher)
                .sectionSubjectAssignment(sectionSubject)
                .assignedDate(LocalDate.now())
                .build();
        assignment.setCreatedBy(createdById);
        assignment.setCreatedAt(java.time.Instant.now());
        assignment.setActive(true);
        assignment.setDeleted(false);

         // If the dto indicates that this teacher is to be assigned as class teacher
        if (dto.getClassTeacher() == Boolean.TRUE ){
            ClassTeacherAssignmentRequest request = new ClassTeacherAssignmentRequest();
            request.setSectionId(sectionSubject.getSection().getId());
            request.setTeacherId(teacherId);
            classTeacherAssignmentService.assignClassTeacher(request, academicSessionName, createdById);
        }
        repo.save(assignment);
    }

    public Page<TeacherSubjectAssignmentResponseDto> listByTeacher(Long teacherId, Pageable pageable, String academicSession) {
        Page<TeacherSubjectAssignment> page =  repo.findByTeacher_Id(teacherId, pageable);
        return page.map(mapper::toDto);
    }
    public Page<TeacherSubjectAssignmentResponseDto> listBySectionSubjectAssignmentId
            (Long sectionSubjectAssignmentId, Pageable pageable) {
        Page<TeacherSubjectAssignment> page =  repo.findBySectionSubjectAssignment_Id(sectionSubjectAssignmentId, pageable);
        return page.map(mapper::toDto);
    }

    public void delete(Long id) {
        repo.deleteById(id);
    }
}
