package com.student.service.exception;

public class NoSuchStudentExists extends Throwable {
    public NoSuchStudentExists(String s) {
        super(s);
    }
}
