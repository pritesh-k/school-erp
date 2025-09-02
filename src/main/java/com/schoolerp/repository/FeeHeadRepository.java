//package com.schoolerp.repository;
//
//import com.schoolerp.entity.FeeHead;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.Pageable;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Query;
//import org.springframework.data.repository.query.Param;
//import org.springframework.stereotype.Repository;
//
//import java.util.List;
//import java.util.Optional;
//
//@Repository
//public interface FeeHeadRepository extends JpaRepository<FeeHead, Long> {
//    List<FeeHead> findByIsActiveTrueOrderByDisplayOrder();
//
//    Optional<FeeHead> findByNameAndIsActiveTrue(String name);
//
//    List<FeeHead> findByCategoryAndIsActiveTrue(String category);
//
//    @Query("SELECT fh FROM FeeHead fh WHERE fh.isActive = true AND " +
//            "(:category IS NULL OR fh.category = :category)")
//    Page<FeeHead> findActiveByCategory(@Param("category") String category, Pageable pageable);
//
//    @Query("SELECT fh FROM FeeHead fh WHERE fh.isActive = true")
//    Page<FeeHead> findActive(Pageable pageable);
//}
