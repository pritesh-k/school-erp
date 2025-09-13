package com.schoolerp.service;
import com.schoolerp.dto.request.StudentCreateDto;
import com.schoolerp.dto.request.StudentUpdateDto;
import com.schoolerp.dto.response.ApiResponse;
import com.schoolerp.dto.response.StudentDetailedResponseDto;
import com.schoolerp.dto.response.StudentResponseDto;
import com.schoolerp.entity.Student;
import com.schoolerp.utils.BulkImportReport;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface StudentService {
    StudentResponseDto create(StudentCreateDto dto, Long userId);
    Page<StudentResponseDto> list(Pageable pageable);
    StudentResponseDto get(Long id);

    Page<StudentResponseDto> searchStudents(String name, boolean notEntrolledOnes, String academicSessionName, Pageable pageable);

    StudentResponseDto update(Long id, StudentUpdateDto dto);
    void delete(Long id);
    void bulkUpload(MultipartFile file);

    Student getByUserId(Long id);

    void existsByIdAndUser_Id(Long studentId, Long userId);

    Long getTotalStudentCount();

    StudentDetailedResponseDto getDetailed(Long id);

    BulkImportReport bulkImport(MultipartFile file, Long userId);
}