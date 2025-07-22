package com.schoolerp.exception;

public class NoChangesDetectedException extends RuntimeException {

    public NoChangesDetectedException(String message) {
        super(message);
    }

    public NoChangesDetectedException(String message, Throwable cause) {
        super(message, cause);
    }
}
