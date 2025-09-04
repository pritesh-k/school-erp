package com.schoolerp.dto.response;

import com.schoolerp.entity.Timetable;
import com.schoolerp.enums.TimetableType;
import lombok.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TimetableDetailedResponseDTO {

    private Long id;

    // Class/Section/Subject Info
    private SectionSubjectAssignmentResponseDto sectionSubjectAssignment;

    // Timetable details
    private TimetableType type;
    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;
    private Timetable.TimeTableStatus status;

    private String location;
    private String note;

    // Exam Info (only if type = EXAM)
    private Long examId;
    private String examName;
}
