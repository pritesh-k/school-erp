//package com.schoolerp.controller;
//
//import com.schoolerp.dto.request.ExamRegistrationCreateDto;
//import com.schoolerp.dto.response.ApiResponse;
//import com.schoolerp.dto.response.ExamRegistrationResponseDto;
//import com.schoolerp.service.ExamRegistrationService;
//import jakarta.validation.Valid;
//import lombok.RequiredArgsConstructor;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.data.domain.Pageable;
//import org.springframework.data.domain.Sort;
//import org.springframework.web.bind.annotation.*;
//
//@RestController
//@RequestMapping("/api/exam-registrations")
//@RequiredArgsConstructor
//public class ExamRegistrationController {
//
//    private final ExamRegistrationService service;
//
//    @PostMapping
//    public ApiResponse<ExamRegistrationResponseDto> create(@RequestBody @Valid ExamRegistrationCreateDto dto) {
//        return ApiResponse.ok(service.create(dto));
//    }
//
//    @GetMapping("/{id}")
//    public ApiResponse<ExamRegistrationResponseDto> get(@PathVariable Long id) {
//        return ApiResponse.ok(service.getById(id));
//    }
//
//    @GetMapping
//    public ApiResponse<Page<ExamRegistrationResponseDto>> list(
//            @RequestParam(defaultValue = "0") int page,
//            @RequestParam(defaultValue = "10") int size) {
//        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
//        return ApiResponse.ok(service.list(pageable));
//    }
//
//    @DeleteMapping("/{id}")
//    public ApiResponse<Void> delete(@PathVariable Long id) {
//        service.delete(id);
//        return ApiResponse.ok(null);
//    }
//}
//
