package com.schoolerp.repository;

import com.schoolerp.entity.AcademicSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AcademicSessionRepository extends JpaRepository<AcademicSession, Long> {
    boolean existsByName(String name);
    Optional<AcademicSession> findByName(String name);

    Optional<AcademicSession> findByIsCurrentTrue();
}