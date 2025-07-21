package com.schoolerp.service;
import com.schoolerp.dto.request.StudentCreateDto;
import com.schoolerp.dto.response.ApiResponse;
import com.schoolerp.dto.response.StudentResponseDto;
import com.schoolerp.entity.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

public interface StudentService {
    StudentResponseDto create(StudentCreateDto dto);
    Page<StudentResponseDto> list(Pageable pageable);
    StudentResponseDto get(Long id);
    StudentResponseDto update(Long id, StudentCreateDto dto);
    void delete(Long id);
    void bulkUpload(MultipartFile file);

    Student getByUserId(Long id);
}