package com.schoolerp.repository;

import com.schoolerp.dto.response.TeachingSectionDto;
import com.schoolerp.entity.SectionTeacherAssignment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SectionTeacherAssignmentRepository extends JpaRepository<SectionTeacherAssignment, Long> {
    List<SectionTeacherAssignment> findBySectionId(Long sectionId);
    Page<SectionTeacherAssignment> findByTeacher_Id(Long teacherId, Pageable pageable);
    Optional<SectionTeacherAssignment> findByTeacherIdAndSectionId(Long teacherId, Long sectionId);

    @Query("""
    SELECT new com.schoolerp.dto.response.TeachingSectionDto(
        s.id, s.name, s.roomNo, s.capacity,
        c.id, c.name
    )
    FROM SectionTeacherAssignment sta
    JOIN sta.section s
    JOIN s.schoolClass c
    WHERE sta.teacher.id = :teacherId
    """)
    Page<TeachingSectionDto> findTeachingSectionsByTeacherId(@Param("teacherId") Long teacherId, Pageable pageable);

}
