package com.schoolerp.dto.request;

import lombok.Data;

import java.util.List;

@Data
public class FeeStructureDto {
    public Long sessionId;
    public Long schoolClassId;
    public List<FeeItem> items; // headId, amount

    public Long getSessionId() {
        return sessionId;
    }

    public void setSessionId(Long sessionId) {
        this.sessionId = sessionId;
    }

    public Long getSchoolClassId() {
        return schoolClassId;
    }

    public void setSchoolClassId(Long schoolClassId) {
        this.schoolClassId = schoolClassId;
    }

    public List<FeeItem> getItems() {
        return items;
    }

    public void setItems(List<FeeItem> items) {
        this.items = items;
    }
}
