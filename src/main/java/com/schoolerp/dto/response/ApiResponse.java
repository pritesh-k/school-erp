package com.schoolerp.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;

import java.io.Serializable;
import java.time.Instant;
import java.util.List;

@JsonInclude(Include.NON_NULL)          // drop null fields
public record ApiResponse<T>(
        int status,
        String message,
        T data,
        Instant timestamp,
        Paging paging,                  // null for non-paged calls
        Error error                     // null for success calls
) {

    public record Paging(Integer page, Integer size, Long totalElements, Integer totalPages)
            implements Serializable {}

    public record Error(String code, String message, List<FieldError> fieldErrors)
            implements Serializable {}

    public record FieldError(String field, String message)
            implements Serializable {}

    /* ---------- factory helpers ---------- */

    public static <T> ApiResponse<T> ok(T data) {
        return new ApiResponse<>(HttpStatus.OK.value(), "Success", data,
                Instant.now(), null, null);
    }

    public static <T> ApiResponse<List<T>> paged(Page<T> page) {
        var paging = new Paging(page.getNumber(), page.getSize(),
                page.getTotalElements(), page.getTotalPages());
        return new ApiResponse<>(HttpStatus.OK.value(), "Success", page.getContent(),
                Instant.now(), paging, null);
    }

    public static <T> ApiResponse<T> error(HttpStatus status, String message) {
        return new ApiResponse<>(status.value(), message, null,
                Instant.now(), null,
                new Error(String.valueOf(status.value()), message, null));
    }

    public static <T> ApiResponse<T> validationError(List<FieldError> fieldErrors) {
        return new ApiResponse<>(HttpStatus.BAD_REQUEST.value(), "Validation failed", null,
                Instant.now(), null,
                new Error("VALIDATION_ERROR", "Invalid input", fieldErrors));
    }
}