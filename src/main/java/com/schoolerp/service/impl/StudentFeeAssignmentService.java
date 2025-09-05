package com.schoolerp.service.impl;

import com.schoolerp.dto.request.FeeAssignDto;
import com.schoolerp.dto.request.FeeAssignUpdateDto;
import com.schoolerp.dto.response.StudentFeeAssignmentResponse;
import com.schoolerp.entity.*;
import com.schoolerp.exception.ResourceNotFoundException;
import com.schoolerp.mapper.StudentFeeAssignmentMapper;
import com.schoolerp.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class StudentFeeAssignmentService {
    @Autowired private StudentRepository studentRepo;
    @Autowired private FeeStructureRepository structureRepo;
    @Autowired private StudentFeeAssignmentRepository assignmentRepo;

    @Autowired private StudentEnrollmentRepository studentEnrollmentRepo;
    @Autowired
    private SchoolClassRepository classRepo;
    @Autowired
    private StudentFeeAssignmentMapper studentFeeAssignmentMapper;
    @Autowired
    private AcademicSessionRepository sessionRepo;

    public StudentFeeAssignmentResponse assign(FeeAssignDto dto, Long createdBy) {
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

    public StudentFeeAssignmentResponse assignUpdate(Long assignmentId, FeeAssignUpdateDto dto, Long updatedBy) {

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

    public StudentFeeAssignmentResponse getByStudentEnrollment(Long studentEnrollId) {
        StudentFeeAssignment assignment = assignmentRepo.findByStudentEnrollment_Id(studentEnrollId);
        return studentFeeAssignmentMapper.toResponse(assignment);
    }

    @Transactional
    public List<StudentFeeAssignmentResponse> bulkAssign(Long classId, Long feeStructureId, String academicSessionName, Long createdBy) {
        AcademicSession session = sessionRepo.findByName(academicSessionName)
                .orElseThrow(() -> new ResourceNotFoundException("Academic session not found: " + academicSessionName));

        SchoolClass schoolClass = classRepo.findById(classId)
                .orElseThrow(() -> new ResourceNotFoundException("Class not found with ID: " + classId));

        FeeStructure feeStructure = structureRepo.findById(feeStructureId)
                .orElseThrow(() -> new ResourceNotFoundException("Fee structure not found with ID: " + feeStructureId));

        // Fetch all student enrollments for this class + session
        List<StudentEnrollment> enrollments = studentEnrollmentRepo
                .findBySchoolClass_IdAndAcademicSession_Id(schoolClass.getId(), session.getId());

        if (enrollments.isEmpty()) {
            throw new ResourceNotFoundException("No students found for class: " + classId + " in session: " + academicSessionName);
        }

        List<StudentFeeAssignmentResponse> responses = new ArrayList<>();

        for (StudentEnrollment enrollment : enrollments) {
            // Skip if already assigned
            boolean alreadyAssigned = assignmentRepo.existsByStudentEnrollment_IdAndFeeStructure_Id(enrollment.getId(), feeStructure.getId());
            if (alreadyAssigned) {
                continue;
            }

            StudentFeeAssignment assignment = StudentFeeAssignment.builder()
                    .studentEnrollment(enrollment)
                    .feeStructure(feeStructure)
                    .assignedDate(LocalDate.now())
                    .build();
            assignment.setDeleted(false);
            assignment.setActive(true);
            assignment.setCreatedBy(createdBy);
            assignment.setCreatedAt(Instant.now());

            assignment = assignmentRepo.save(assignment);
            responses.add(studentFeeAssignmentMapper.toResponse(assignment));
        }
        return responses;
    }

}
