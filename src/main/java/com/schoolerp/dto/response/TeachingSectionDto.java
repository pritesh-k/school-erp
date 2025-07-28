package com.schoolerp.dto.response;

import com.schoolerp.dto.BaseDTO;
import com.schoolerp.enums.ClassStandard;
import com.schoolerp.enums.SectionName;

public class TeachingSectionDto extends BaseDTO {
    private Long sectionId;
    private SectionName sectionName;
    private String roomNo;
    private Integer capacity;
    private Long classId;
    private ClassStandard classStandard;

    public TeachingSectionDto(Long sectionId, SectionName sectionName, String roomNo,
                              Integer capacity, Long classId, ClassStandard classStandard) {
        this.sectionId = sectionId;
        this.sectionName = sectionName;
        this.roomNo = roomNo;
        this.capacity = capacity;
        this.classId = classId;
        this.classStandard = classStandard;
    }

    public Long getSectionId() {
        return sectionId;
    }

    public void setSectionId(Long sectionId) {
        this.sectionId = sectionId;
    }

    public SectionName getSectionName() {
        return sectionName;
    }

    public void setSectionName(SectionName sectionName) {
        this.sectionName = sectionName;
    }

    public String getRoomNo() {
        return roomNo;
    }

    public void setRoomNo(String roomNo) {
        this.roomNo = roomNo;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public Long getClassId() {
        return classId;
    }

    public void setClassId(Long classId) {
        this.classId = classId;
    }

    public ClassStandard getClassStandard() {
        return classStandard;
    }

    public void setClassStandard(ClassStandard classStandard) {
        this.classStandard = classStandard;
    }
}
