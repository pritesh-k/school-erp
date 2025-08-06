package com.schoolerp.service;

import com.schoolerp.dto.request.StudentEnrollmentDTO;
import org.springframework.data.domain.Page;

public interface StudentEnrollmentService {
    Page<StudentEnrollmentDTO> getAll(Long sessionId, int page, int size);
    StudentEnrollmentDTO getById(Long id);
    StudentEnrollmentDTO create(StudentEnrollmentDTO dto);
    StudentEnrollmentDTO update(Long id, StudentEnrollmentDTO dto);
    void delete(Long id);
}

