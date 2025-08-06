package com.schoolerp.repository;

import com.schoolerp.entity.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

    // Search students by name
    @Query("SELECT s FROM Student s WHERE LOWER(s.firstName) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    Page<Student> searchByName(@Param("keyword") String keyword, Pageable pageable);

    Optional<Student> findByAdmissionNumber(String admissionNumber);

    Optional<Student> findByUserId(Long userId);

    boolean existsByIdAndUser_Id(Long id, Long userId);
}