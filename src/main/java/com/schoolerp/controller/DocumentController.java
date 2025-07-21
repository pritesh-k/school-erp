package com.schoolerp.controller;

import com.schoolerp.dto.response.ApiResponse;
import com.schoolerp.dto.response.DocumentResponseDto;
import com.schoolerp.service.DocumentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/v1/documents")
@RequiredArgsConstructor
public class DocumentController {
    private final DocumentService service;

    @PostMapping
    public ApiResponse<DocumentResponseDto> upload(@RequestParam("file") MultipartFile file,
                                                   @RequestParam("studentId") Long studentId,
                                                   @RequestParam("type") String type) {
        return ApiResponse.ok(service.upload(file, studentId, type));
    }

    @GetMapping("/student/{studentId}")
    public ApiResponse<List<DocumentResponseDto>> listByStudent(
            @PathVariable Long studentId,
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "date"));

        return ApiResponse.paged(service.listByStudent(studentId, pageable));
    }
}