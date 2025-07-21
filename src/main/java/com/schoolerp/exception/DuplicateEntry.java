package com.schoolerp.exception;

public class DuplicateEntry extends RuntimeException{
    public DuplicateEntry(String msg) {
        super(msg);
    }
}
