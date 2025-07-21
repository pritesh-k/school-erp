package com.schoolerp.dto.response;

import com.schoolerp.dto.BaseDTO;
import com.schoolerp.enums.SectionName;

public class SectionResponseDto extends BaseDTO {
    SectionName name; String roomNo; int capacity;
    String className; String teacherName;

    public SectionResponseDto(SectionName name, String roomNo, int capacity, String className, String teacherName) {
        this.name = name;
        this.roomNo = roomNo;
        this.capacity = capacity;
        this.className = className;
        this.teacherName = teacherName;
    }

    public SectionName getName() {
        return name;
    }

    public void setName(SectionName name) {
        this.name = name;
    }

    public String getRoomNo() {
        return roomNo;
    }

    public void setRoomNo(String roomNo) {
        this.roomNo = roomNo;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }
}