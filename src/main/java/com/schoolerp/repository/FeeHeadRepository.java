package com.schoolerp.repository;

import com.schoolerp.entity.FeeHead;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface FeeHeadRepository extends JpaRepository<FeeHead, Long> {
    @Query("SELECT fh FROM FeeHead fh WHERE fh.isActive = true")
    Page<FeeHead> findActive(Pageable pageable);
}
