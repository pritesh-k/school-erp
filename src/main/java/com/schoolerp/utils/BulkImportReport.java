package com.schoolerp.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BulkImportReport {
    private int totalRows;
    private int successCount;
    private int failureCount;
    private List<RowError> errors;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class RowError {
        private int rowNumber;
        private String errorMessage;
    }
}

