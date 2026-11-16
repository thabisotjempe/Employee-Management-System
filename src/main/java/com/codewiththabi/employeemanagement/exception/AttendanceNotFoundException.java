package com.codewiththabi.employeemanagement.exception;

public class AttendanceNotFoundException
        extends RuntimeException {

    public AttendanceNotFoundException() {
        super("Attendance record was not found");
    }
}
