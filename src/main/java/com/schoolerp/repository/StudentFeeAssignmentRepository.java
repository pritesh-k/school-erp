package com.schoolerp.repository;

import com.schoolerp.entity.StudentFeeAssignment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentFeeAssignmentRepository extends JpaRepository<StudentFeeAssignment, Long>, JpaSpecificationExecutor<StudentFeeAssignment> {}
