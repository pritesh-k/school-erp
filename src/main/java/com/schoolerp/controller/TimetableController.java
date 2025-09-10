package com.schoolerp.controller;

import com.schoolerp.dto.request.CreateTimetableDTO;
import com.schoolerp.dto.request.UpdateTimetableDTO;
import com.schoolerp.dto.response.ApiResponse;
import com.schoolerp.dto.response.TimetableDetailedResponseDTO;
import com.schoolerp.dto.response.TimetableResponseDTO;
import com.schoolerp.entity.UserTypeInfo;
import com.schoolerp.enums.TimetableType;
import com.schoolerp.service.RequestContextService;
import com.schoolerp.service.TimetableService;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/timetables")
@RequiredArgsConstructor
public class TimetableController {

    private final TimetableService timetableService;

    @Autowired
    private final RequestContextService requestContextService;

    @PostMapping
    public ApiResponse<TimetableResponseDTO> createTimetable(@RequestBody CreateTimetableDTO dto) {
        UserTypeInfo userTypeInfo = requestContextService.getCurrentUserContext();
        Long createdByUserId = userTypeInfo.getUserId();
        String academicSessionName = userTypeInfo.getAcademicSession();
        return ApiResponse.ok(timetableService.createTimetable(dto, createdByUserId, academicSessionName));
    }

    @PutMapping("/{id}")
    public ApiResponse<TimetableResponseDTO> updateTimetable(
            @PathVariable Long id,
            @RequestBody UpdateTimetableDTO dto) {
        UserTypeInfo userTypeInfo = requestContextService.getCurrentUserContext();
        Long updatedByUserId = userTypeInfo.getUserId();

        return ApiResponse.ok(timetableService.updateTimetable(id, dto, updatedByUserId));
    }

    @PutMapping("/{id}/activate")
    public ApiResponse<TimetableResponseDTO> activateTimetable(
            @PathVariable Long id) {
        UserTypeInfo userTypeInfo = requestContextService.getCurrentUserContext();
        Long updatedByUserId = userTypeInfo.getUserId();
        TimetableResponseDTO result = timetableService.activateTimetable(id, updatedByUserId);
        return ApiResponse.ok(result);
    }

    @GetMapping("/search")
    public ApiResponse<List<TimetableDetailedResponseDTO>> searchTimetables(
            @RequestParam(required = false) TimetableType type,
            @RequestParam(required = false) Long classId,
            @RequestParam(required = false) Long sectionId,
            @RequestParam(required = false) Long subjectId,
            @RequestParam(required = false, defaultValue = "0") @Min(0) Integer page,
            @RequestParam(required = false, defaultValue = "10") @Min(1) @Max(10) Integer size) {
        UserTypeInfo userTypeInfo = requestContextService.getCurrentUserContext();
        String academicSessionName = userTypeInfo.getAcademicSession();

        Pageable pageable = PageRequest.of(page, size);

        Page<TimetableDetailedResponseDTO> result = timetableService.searchTimetables(classId,
                sectionId, subjectId,type, academicSessionName, pageable);
        return ApiResponse.paged(result);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TimetableDetailedResponseDTO> getTimetable(@PathVariable Long id) {
        UserTypeInfo userTypeInfo = requestContextService.getCurrentUserContext();

        return ResponseEntity.ok(timetableService.getTimetableById(id));
    }

    @GetMapping
    public ApiResponse<List<TimetableDetailedResponseDTO>> getAllTimetables(
            @RequestParam(required = false, defaultValue = "0") @Min(0) Integer page,
            @RequestParam(required = false, defaultValue = "10") @Min(1) @Max(10) Integer size) {
        UserTypeInfo userTypeInfo = requestContextService.getCurrentUserContext();
        String academicSessionName = userTypeInfo.getAcademicSession();
        Pageable pageable = PageRequest.of(page, size);
        Page<TimetableDetailedResponseDTO> result = timetableService.getAllTimetables(academicSessionName, pageable);
        return ApiResponse.paged(result);
    }
}

