package com.schoolerp.dto.response.enrollments;

import com.schoolerp.dto.BaseDTO;
import com.schoolerp.dto.response.ClassResponseDto;
import com.schoolerp.dto.response.SectionResponseDto;
import com.schoolerp.newdtos.academic.AcademicSessionResponseDto;

public class StudentEnrollmentResDto extends BaseDTO {
    private Long studentId;
    private AcademicSessionResponseDto academicSession;
    private StudentResponse student;
    private SectionResponseDto section;

    private ClassResponseDto schoolClass;

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public AcademicSessionResponseDto getAcademicSession() {
        return academicSession;
    }

    public void setAcademicSession(AcademicSessionResponseDto academicSession) {
        this.academicSession = academicSession;
    }

    public StudentResponse getStudent() {
        return student;
    }

    public void setStudent(StudentResponse student) {
        this.student = student;
    }

    public SectionResponseDto getSection() {
        return section;
    }

    public void setSection(SectionResponseDto section) {
        this.section = section;
    }

    public ClassResponseDto getSchoolClass() {
        return schoolClass;
    }

    public void setSchoolClass(ClassResponseDto schoolClass) {
        this.schoolClass = schoolClass;
    }
}
