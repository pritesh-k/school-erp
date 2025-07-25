package com.schoolerp.exception;

import com.schoolerp.dto.response.ApiResponse;
import com.schoolerp.dto.response.ApiResponse.FieldError;
import org.slf4j.MDC;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse<Void>> handleNotFound(ResourceNotFoundException ex) {
        return respond(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Void>> handleValidation(MethodArgumentNotValidException ex) {
        List<FieldError> errs = ex.getBindingResult().getFieldErrors().stream()
                .map(f -> new FieldError(f.getField(), f.getDefaultMessage()))
                .toList();
        return ResponseEntity.badRequest()
                .body(ApiResponse.validationError(errs));
    }

    @ExceptionHandler(DuplicateEntry.class)
    public ResponseEntity<ApiResponse<Void>> handleDuplicateEntries(MethodArgumentNotValidException ex) {
        return respond(HttpStatus.CONFLICT, ex.getMessage());
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<ApiResponse<Void>> handleUnauthorizedException(MethodArgumentNotValidException ex) {
        return respond(HttpStatus.UNAUTHORIZED, ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Void>> handleUnhandled(Exception ex) {
        return respond(HttpStatus.INTERNAL_SERVER_ERROR,
                "Unexpected error: " + ex.getMessage());
    }

    @ExceptionHandler({NoChangesDetectedException.class, SQLException.class})
    public ResponseEntity<ApiResponse<Void>> handleNoChangesDetectedException(MethodArgumentNotValidException ex) {
        return respond(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    /* utility to add request-id header */
    private static ResponseEntity<ApiResponse<Void>> respond(HttpStatus status, String msg) {
        return ResponseEntity.status(status)
                .header("X-Request-Id", MDC.get("requestId"))
                .body(ApiResponse.error(status, msg));
    }
}