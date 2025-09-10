package com.schoolerp.dto.request;

import com.schoolerp.enums.TimetableType;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateTimetableDTO {
    @NotNull
    private TimetableType type;
    @NotNull
    private LocalDate date;
    @NotNull
    private LocalTime startTime;

    private LocalTime endTime;

    private String location;

    private String note;
}

