package com.schoolerp.repository;

import com.schoolerp.entity.FeeStructure;
import com.schoolerp.entity.StudentFeeAssignment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentFeeAssignmentRepository extends JpaRepository<StudentFeeAssignment, Long>, JpaSpecificationExecutor<StudentFeeAssignment> {
    StudentFeeAssignment findByStudentEnrollment_IdAndFeeStructure_Id(Long id, FeeStructure feeStructure);
    Page<StudentFeeAssignment> findByStudentEnrollment_Student_Id(Long studentId, Pageable pageable);
    boolean existsByStudentEnrollment_IdAndFeeStructure_Id(Long id, Long feeStructureId);
}
