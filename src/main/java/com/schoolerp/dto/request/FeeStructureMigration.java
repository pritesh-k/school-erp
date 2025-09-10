package com.schoolerp.dto.request;

import jakarta.validation.constraints.NotNull;

public class FeeStructureMigration {
    @NotNull
    private long feeStructureId;
    @NotNull
    private long academicSessionId;

    public long getFeeStructureId() {
        return feeStructureId;
    }

    public void setFeeStructureId(long feeStructureId) {
        this.feeStructureId = feeStructureId;
    }

    public long getAcademicSessionId() {
        return academicSessionId;
    }

    public void setAcademicSessionId(long academicSessionId) {
        this.academicSessionId = academicSessionId;
    }
}
