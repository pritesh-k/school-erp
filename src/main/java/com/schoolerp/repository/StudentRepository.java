package com.schoolerp.repository;

import com.schoolerp.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long>, JpaSpecificationExecutor<Student> {

    Optional<Student> findByAdmissionNumber(String admissionNumber);

    Optional<Student> findByUserId(Long userId);

    boolean existsByIdAndUser_Id(Long id, Long userId);
}