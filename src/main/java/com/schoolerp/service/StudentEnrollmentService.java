package com.schoolerp.service;

import com.schoolerp.dto.request.BulkStudentEnrollmentCreateDTO;
import com.schoolerp.dto.request.StudentEnrollmentCreateDTO;
import com.schoolerp.dto.request.StudentEnrollmentDTO;
import com.schoolerp.dto.request.StudentEnrollmentUpdateDTO;
import com.schoolerp.dto.response.enrollments.StudentEnrollmentResDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface StudentEnrollmentService {
    Page<StudentEnrollmentDTO> getAll(Long sessionId, int page, int size);
    StudentEnrollmentDTO getById(Long id);
    StudentEnrollmentDTO getByStudentIdAndAcademicSession(Long studentId, String academicSession);
    StudentEnrollmentDTO create(StudentEnrollmentCreateDTO dto, Long createdByUserId);

    List<StudentEnrollmentDTO> bulkCreate(BulkStudentEnrollmentCreateDTO dto, Long createdByUserId, String academicSession);

    StudentEnrollmentDTO update(StudentEnrollmentUpdateDTO dto, Long updatedByUserId, String academicSession);
    void delete(Long id);
    Long getTotalStudentCount(String academicSession, Long sectionId, Long classId, Long studentId);

    Page<StudentEnrollmentResDto> getEnrollments(
            String academicSessionName,
            Long sectionId,
            Long classId,
            Long studentId,
            Pageable pageable);
}

