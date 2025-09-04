package com.schoolerp.service;

import com.schoolerp.dto.request.CreateTimetableDTO;
import com.schoolerp.dto.request.UpdateTimetableDTO;
import com.schoolerp.dto.response.TimetableDetailedResponseDTO;
import com.schoolerp.dto.response.TimetableResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

public interface TimetableService {

    TimetableResponseDTO createTimetable(CreateTimetableDTO dto, Long createdByUserId);

    TimetableResponseDTO createTimetableForSubject(Long createdByUserId,
                                                   Long sectionSubjectAssignmentId);

    TimetableResponseDTO updateTimetable(Long id, UpdateTimetableDTO dto, Long updatedByUserId);

    TimetableResponseDTO activateTimetable(Long id, Long updatedByUserId);

    void deleteTimetable(Long id, Long deletedByUserId);

    TimetableDetailedResponseDTO getTimetableById(Long id);

    @Transactional(readOnly = true)
    Page<TimetableDetailedResponseDTO> getAllTimetables(String academicSessionName, Pageable pageable);

    Page<TimetableDetailedResponseDTO> searchTimetables(Long classId, Long sectionId, Long subjectId,
                                                        String academicSessionName, Pageable pageable);
}
