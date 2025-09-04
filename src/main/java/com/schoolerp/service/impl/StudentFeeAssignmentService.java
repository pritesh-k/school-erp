package com.schoolerp.service.impl;

import com.schoolerp.dto.request.FeeAssignDto;
import com.schoolerp.dto.response.StudentFeeAssignmentResponse;
import com.schoolerp.entity.FeeStructure;
import com.schoolerp.entity.Student;
import com.schoolerp.entity.StudentEnrollment;
import com.schoolerp.entity.StudentFeeAssignment;
import com.schoolerp.exception.ResourceNotFoundException;
import com.schoolerp.mapper.StudentFeeAssignmentMapper;
import com.schoolerp.repository.FeeStructureRepository;
import com.schoolerp.repository.StudentEnrollmentRepository;
import com.schoolerp.repository.StudentFeeAssignmentRepository;
import com.schoolerp.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;
import java.util.Optional;

@Service
public class StudentFeeAssignmentService {
    @Autowired private StudentRepository studentRepo;
    @Autowired private FeeStructureRepository structureRepo;
    @Autowired private StudentFeeAssignmentRepository assignmentRepo;

    @Autowired private StudentEnrollmentRepository studentEnrollmentRepo;

    @Autowired
    private StudentFeeAssignmentMapper studentFeeAssignmentMapper;

    public StudentFeeAssignmentResponse assign(FeeAssignDto dto, Long createdBy) {
        Optional<Student> s = studentRepo.findById(dto.getStudentId());
        if (s.isEmpty()) {
            throw new ResourceNotFoundException("Student not found with ID: " + dto.getStudentId());
        }
        Optional<FeeStructure> fs = structureRepo.findById(dto.getFeeStructureId());
        if (fs.isEmpty()) {
            throw new ResourceNotFoundException("Fee structure not found with ID: " + dto.getFeeStructureId());
        }
        StudentEnrollment enrollment = studentEnrollmentRepo.findById(dto.getStudentEnrollmentId())
                .orElseThrow(() -> new ResourceNotFoundException("Student enrollment not found with ID: " + dto.getStudentEnrollmentId()));

        if (assignmentRepo.existsByStudentEnrollment_IdAndFeeStructure_Id(enrollment.getId(), fs.get().getId())) {
            throw new ResourceNotFoundException("Fee structure already assigned to this student.");
        }
        StudentFeeAssignment assignment = StudentFeeAssignment.builder()
                .studentEnrollment(enrollment)
                .feeStructure(fs.get())
                .assignedDate(LocalDate.now())
                .build();

        assignment.setActive(true);
        assignment.setDeleted(false);
        assignment.setCreatedAt(Instant.now());
        assignment.setCreatedBy(createdBy);

        if (dto.getDiscountAmount() != null) {
            assignment.setDiscountAmount(dto.getDiscountAmount());
        }
        if (dto.getDiscountReason() != null) {
            assignment.setDiscountReason(dto.getDiscountReason());
        }

        assignment = assignmentRepo.save(assignment);
        return studentFeeAssignmentMapper.toResponse(assignment);
    }

    public StudentFeeAssignmentResponse assignUpdate(Long assignmentId, FeeAssignDto dto, Long updatedBy) {

        StudentFeeAssignment assignment = assignmentRepo.findById(assignmentId)
                .orElseThrow(() -> new ResourceNotFoundException("Student fee assignment not found with ID: " + assignmentId));

        boolean hasChanges = false;
        if (dto.getDiscountReason() != null){
            assignment.setDiscountReason(dto.getDiscountReason());
            hasChanges = true;
        }
        if (dto.getDiscountAmount() != null && dto.getDiscountAmount().compareTo(assignment.getDiscountAmount()) != 0){
            assignment.setDiscountAmount(dto.getDiscountAmount());
            hasChanges = true;
        }

        if (hasChanges) {
            assignment.setUpdatedAt(Instant.now());
            assignment.setUpdatedBy(updatedBy);
            assignment = assignmentRepo.save(assignment);

        }
        return studentFeeAssignmentMapper.toResponse(assignment);
    }

    public StudentFeeAssignmentResponse getByStudentEnrollment(Long studentId) {
        StudentFeeAssignment assignment = assignmentRepo.findByStudentEnrollment_Id(studentId);
        return studentFeeAssignmentMapper.toResponse(assignment);
    }
}
