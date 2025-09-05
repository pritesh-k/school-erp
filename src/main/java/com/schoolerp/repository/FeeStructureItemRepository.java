package com.schoolerp.repository;

import com.schoolerp.entity.FeeStructureItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FeeStructureItemRepository extends JpaRepository<FeeStructureItem, Long> {

    boolean existsByFeeStructure_IdAndFeeHead_Id(Long feeStructureId, Long id);

    Page<FeeStructureItem> findByFeeStructure_Id(Long feeStructureId, Pageable pageable);
}