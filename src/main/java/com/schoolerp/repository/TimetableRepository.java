package com.schoolerp.repository;

import com.schoolerp.entity.Timetable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TimetableRepository extends JpaRepository<Timetable, Long> {
    Page<Timetable> findByAcademicSession_Id(Long academicSessionId, Pageable pageable);

    @Query("""
       SELECT t
       FROM Timetable t
       JOIN t.academicSession sess
       JOIN t.sectionSubjectAssignment ssa
       JOIN ssa.section sec
       JOIN sec.schoolClass cls
       JOIN ssa.subject subj
       LEFT JOIN t.exam e
       WHERE (:classId IS NULL OR cls.id = :classId)
         AND (:sectionId IS NULL OR sec.id = :sectionId)
         AND (:subjectId IS NULL OR subj.id = :subjectId)
         AND (:academicSessionName IS NULL OR sess.name = :academicSessionName)
    """)
    Page<Timetable> searchTimetables(@Param("classId") Long classId,
                                     @Param("sectionId") Long sectionId,
                                     @Param("subjectId") Long subjectId,
                                     @Param("academicSessionName") String academicSessionName,
                                     Pageable pageable);

}
