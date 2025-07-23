package com.schoolerp.service.impl;

import com.schoolerp.dto.request.FeeAssignDto;
import com.schoolerp.entity.FeeStructure;
import com.schoolerp.entity.Student;
import com.schoolerp.entity.StudentFeeAssignment;
import com.schoolerp.repository.FeeStructureRepository;
import com.schoolerp.repository.StudentFeeAssignmentRepository;
import com.schoolerp.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentFeeAssignmentService {
    @Autowired private StudentRepository studentRepo;
    @Autowired private FeeStructureRepository structureRepo;
    @Autowired private StudentFeeAssignmentRepository assignmentRepo;

    public StudentFeeAssignment assign(FeeAssignDto dto) {
        Student s = studentRepo.findById(dto.getStudentId()).orElseThrow();
        FeeStructure fs = structureRepo.findById(dto.getFeeStructureId()).orElseThrow();
        StudentFeeAssignment assignment = StudentFeeAssignment.builder()
                .student(s)
                .feeStructure(fs)
                .discount(dto.getDiscount())
                .build();
        return assignmentRepo.save(assignment);
    }
    public List<StudentFeeAssignment> listByStudent(Long studentId) {
        return assignmentRepo.findAll((root, query, cb) -> cb.equal(root.get("student").get("id"), studentId));
    }
    public void delete(Long id) { assignmentRepo.deleteById(id); }
}
