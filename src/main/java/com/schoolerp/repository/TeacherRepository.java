package com.schoolerp.repository;

import com.schoolerp.entity.Teacher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TeacherRepository extends JpaRepository<Teacher, Long> {
    Optional<Teacher> findById(Long teacherId);
    Optional<Teacher> findByEmployeeCode(String employeeCode);

    // Search teachers by name
    @Query("""
       SELECT t FROM Teacher t
       WHERE LOWER(t.firstName) LIKE LOWER(CONCAT('%', :name, '%'))
          OR LOWER(t.lastName) LIKE LOWER(CONCAT('%', :name, '%'))
    """)
    Page<Teacher> searchByName(@Param("name") String name, Pageable pageable);

    @Query("""
        SELECT t
        FROM Teacher t
        WHERE NOT EXISTS (
            SELECT tsa.id
            FROM TeacherSubjectAssignment tsa
            WHERE tsa.teacher = t
              AND tsa.sectionSubjectAssignment.id = :sectionSubjectAssignmentId
        )
    """)
    Page<Teacher> findTeachersWithoutAssignment(@Param("sectionSubjectAssignmentId") Long sectionSubjectAssignmentId, Pageable pageable);

    boolean existsByIdAndUser_Id(Long id, Long userId);

    Optional<Teacher> findByUserId(Long recordedByUserId);
}
