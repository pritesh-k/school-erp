package com.schoolerp.dto.request;

import com.schoolerp.enums.TimetableType;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateTimetableDTO {

    private TimetableType type;

    private LocalDate date;

    private LocalTime startTime;

    private LocalTime endTime;

    private String location;

    private String note;
}

