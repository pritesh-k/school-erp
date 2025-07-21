package com.schoolerp.repository;

import com.schoolerp.entity.Parent;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface ParentRepository extends JpaRepository<Parent, Long> {
    Page<Parent> findByFirstNameContainingIgnoreCase(String keyword, Pageable pageable);
}