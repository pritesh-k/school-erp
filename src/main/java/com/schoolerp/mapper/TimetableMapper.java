package com.schoolerp.mapper;

import com.schoolerp.dto.response.TimetableDetailedResponseDTO;
import com.schoolerp.dto.response.TimetableResponseDTO;
import com.schoolerp.entity.Timetable;
import org.mapstruct.*;

@Mapper(componentModel = "spring", uses = {AssignmentMapper.class})
public interface TimetableMapper {
    TimetableResponseDTO toResponseDto(Timetable entity);

    TimetableDetailedResponseDTO toDetailedResponse(Timetable entity);
}

