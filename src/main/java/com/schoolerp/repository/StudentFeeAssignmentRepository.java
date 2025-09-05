package com.schoolerp.repository;

import com.schoolerp.entity.StudentFeeAssignment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentFeeAssignmentRepository extends JpaRepository<StudentFeeAssignment, Long> {
    @Query("SELECT sfa FROM StudentFeeAssignment sfa " +
            "JOIN FETCH sfa.studentEnrollment se " +
            "JOIN FETCH sfa.feeStructure fs " +
            "JOIN FETCH fs.items fsi " +
            "JOIN FETCH fsi.feeHead fh " +
            "WHERE se.student.id = :studentId AND fs.academicSession.id = :sessionId")
    Optional<StudentFeeAssignment> findByStudentAndSessionWithDetails(
            @Param("studentId") Long studentId, @Param("sessionId") Long sessionId);

    boolean existsByStudentEnrollment_IdAndFeeStructure_Id(Long id, Long id1);

    StudentFeeAssignment findByStudentEnrollment_Id(Long studentEnrollmentId);
}

