package com.schoolerp.service;

import com.schoolerp.dto.response.DocumentResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface DocumentService {
    DocumentResponseDto upload(MultipartFile file, Long studentId, String type);
    Page<DocumentResponseDto> listByStudent(Long studentId, Pageable pageable);
}