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
    @Query("SELECT t FROM Teacher t WHERE LOWER(t.firstName) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    Page<Teacher> searchByName(@Param("keyword") String keyword, Pageable pageable);
}
