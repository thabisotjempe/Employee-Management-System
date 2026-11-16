package com.codewiththabi.employeemanagement.exception;


public class InvalidLeaveStatusException extends RuntimeException {

    public InvalidLeaveStatusException(String message) {
        super(message);
    }
}
