package com.schoolerp.service.impl;

import com.schoolerp.dto.request.FeeAssignDto;
import com.schoolerp.entity.FeeStructure;
import com.schoolerp.entity.Student;
import com.schoolerp.entity.StudentEnrollment;
import com.schoolerp.entity.StudentFeeAssignment;
import com.schoolerp.exception.ResourceNotFoundException;
import com.schoolerp.repository.FeeStructureRepository;
import com.schoolerp.repository.StudentEnrollmentRepository;
import com.schoolerp.repository.StudentFeeAssignmentRepository;
import com.schoolerp.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Service
public class StudentFeeAssignmentService {
    @Autowired private StudentRepository studentRepo;
    @Autowired private FeeStructureRepository structureRepo;
    @Autowired private StudentFeeAssignmentRepository assignmentRepo;

    @Autowired private StudentEnrollmentRepository studentEnrollmentRepo;

    public StudentFeeAssignment assign(FeeAssignDto dto) {
        Optional<Student> s = studentRepo.findById(dto.getStudentId());
        if (s.isEmpty()) {
            throw new ResourceNotFoundException("Student not found with ID: " + dto.getStudentId());
        }
        Optional<FeeStructure> fs = structureRepo.findById(dto.getFeeStructureId());
        if (fs.isEmpty()) {
            throw new ResourceNotFoundException("Fee structure not found with ID: " + dto.getFeeStructureId());
        }
        StudentEnrollment enrollment = studentEnrollmentRepo.findByStudent_Id(s.get().getId())
                .orElseThrow(() -> new ResourceNotFoundException("No enrollment found for student ID: " + s.get().getId()));
        if (assignmentRepo.existsByStudentEnrollment_IdAndFeeStructure_Id(enrollment.getId(), fs.get().getId())) {
            throw new ResourceNotFoundException("Fee structure already assigned to this student.");
        }
        StudentFeeAssignment assignment = StudentFeeAssignment.builder()
                .studentEnrollment(enrollment)
                .feeStructure(fs.get())
                .discount(dto.getDiscount())
                .build();
        assignment.setActive(true);
        assignment.setDeleted(false);
        assignment.setCreatedAt(Instant.now());

        return assignmentRepo.save(assignment);
    }
    public Page<StudentFeeAssignment> listByStudent(Long studentId, Pageable pageable) {
        return assignmentRepo.findByStudentEnrollment_Student_Id(studentId, pageable);
    }
    public void delete(Long id) { assignmentRepo.deleteById(id); }
}
