package com.student.service.exception;

public class StudentExistsInDataBase extends Throwable {
    public StudentExistsInDataBase(String s) {
        super(s);
    }

}
