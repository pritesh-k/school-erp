package com.schoolerp.repository;

import com.schoolerp.entity.FeeHead;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FeeHeadRepository extends JpaRepository<FeeHead, Long> {}
