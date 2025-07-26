package com.schoolerp.service;

import com.schoolerp.dto.request.ParentCreateDto;
import com.schoolerp.dto.response.ApiResponse;
import com.schoolerp.dto.response.ParentResponseDto;
import com.schoolerp.entity.Parent;
import com.schoolerp.entity.Student;
import com.schoolerp.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ParentService {
    ParentResponseDto create(ParentCreateDto dto, Long createdById, User savedUser);
    ParentResponseDto get(Long id);
    Page<ParentResponseDto> list(Pageable pageable);
    ParentResponseDto update(Long id, ParentCreateDto dto);
    void delete(Long id);

    Parent getByUserId(Long id);

    Parent getReferenceById(Long id);
}