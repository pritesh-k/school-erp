package com.schoolerp.dto.response;

import com.schoolerp.dto.BaseDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExamRegistrationResponseDto extends BaseDTO {
    private Long examSlotId;
    private Long studentEnrollmentId;

    public Long getExamSlotId() {
        return examSlotId;
    }

    public void setExamSlotId(Long examSlotId) {
        this.examSlotId = examSlotId;
    }

    public Long getStudentEnrollmentId() {
        return studentEnrollmentId;
    }

    public void setStudentEnrollmentId(Long studentEnrollmentId) {
        this.studentEnrollmentId = studentEnrollmentId;
    }
}
