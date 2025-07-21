package com.schoolerp.dto.response.attendance;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SectionBasicDto {
    private Long id;
    private String name;
    private String className;
}
