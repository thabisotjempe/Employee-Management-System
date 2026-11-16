package com.codewiththabi.employeemanagement.exception;

public class UnauthorizedLeaveAccessException
        extends RuntimeException {

    public UnauthorizedLeaveAccessException() {
        super("You are not allowed to access this leave request");
    }
}
