package com.schoolerp.repository;

import com.schoolerp.entity.FeeStructureItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface FeeStructureItemRepository extends JpaRepository<FeeStructureItem, Long> {
    @Query("""
        SELECT f FROM FeeStructureItem f
        WHERE (:feeStructureId IS NULL OR f.feeStructure.id = :feeStructureId)
    """)
    Page<FeeStructureItem> findAllByFilter(@Param("feeStructureId") Long feeStructureId,
                                           Pageable pageable);
}