package com.schoolerp.repository;

import com.schoolerp.entity.Subject;
import com.schoolerp.entity.Teacher;
import com.schoolerp.enums.SubjectCode;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SubjectRepository extends JpaRepository<Subject, Long> {
    Optional<Subject> findByCode(String code);

    @Query(value = """
    SELECT EXISTS (
        SELECT 1 FROM subject_teacher st 
        JOIN subjects s ON s.id = st.subject_id 
        WHERE st.teacher_id = :teacherId 
        AND s.deleted = false
    )
    """, nativeQuery = true)
    boolean existsByTeachersAssignedIdAndDeletedFalse(@Param("teacherId") Long teacherId);

    List<Subject> findByTeachersAssignedId(Long teacherId);
    boolean existsByCode(SubjectCode code);

    @Query("SELECT s FROM Subject s JOIN s.teachersAssigned t WHERE t.id = :teacherId AND s.deleted = false")
    List<Subject> findByTeachersAssignedIdAndDeletedFalse(@Param("teacherId") Long teacherId);

    // 1) Get all teachers assigned to a subject
    @Query("""
        SELECT t FROM Subject s
        JOIN s.teachersAssigned t
        WHERE s.id = :subjectId
    """)
    Page<Teacher> findTeachersBySubjectId(@Param("subjectId") Long subjectId, Pageable pageable);

    // 3) Get all subjects assigned to a teacher
    @Query("""
        SELECT s FROM Subject s
        JOIN s.teachersAssigned t
        WHERE t.id = :teacherId
    """)
    Page<Subject> findSubjectsByTeacherId(@Param("teacherId") Long teacherId, Pageable pageable);

    // 2) Helper query for getting subject teachers by section
    @Query("""
        SELECT DISTINCT t FROM Subject s
        JOIN s.teachersAssigned t
        JOIN s.classes c
        JOIN c.sections sec
        WHERE sec.id = :sectionId
    """)
    Page<Teacher> findSubjectTeachersBySectionId(@Param("sectionId") Long sectionId, Pageable pageable);

}