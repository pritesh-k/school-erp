package com.schoolerp.dto.request;

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
public class UpdateTimetableDTO {

    private TimetableType type;

    private LocalDate date;

    private LocalTime startTime;

    private LocalTime endTime;

    private String location;

    private String note;

    private Timetable.TimeTableStatus status;
}
