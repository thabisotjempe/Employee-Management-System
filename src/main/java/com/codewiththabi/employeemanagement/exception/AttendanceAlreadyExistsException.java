package com.codewiththabi.employeemanagement.exception;

public class AttendanceAlreadyExistsException
        extends RuntimeException {

    public AttendanceAlreadyExistsException() {
        super("Attendance has already been recorded for today");
    }
}
