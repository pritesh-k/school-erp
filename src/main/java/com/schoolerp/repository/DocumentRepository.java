package com.schoolerp.repository;

import com.schoolerp.entity.Document;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;

@Repository
public interface DocumentRepository extends JpaRepository<Document, Long> {
    Page<Document> findByStudentId(Long studentId, Pageable pageable);

    Page<Document> findByUploadedById(Long userId, Pageable pageable);

    Page<Document> findByTypeContainingIgnoreCase(String type, Pageable pageable); // optional for filtering

}
