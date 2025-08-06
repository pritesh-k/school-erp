package com.schoolerp.dto.request;
import com.schoolerp.enums.SectionName;
import jakarta.validation.constraints.*;
public class SectionCreateDto{
    @NotBlank SectionName name;
    String roomNo;
    Integer  capacity;

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

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }
}