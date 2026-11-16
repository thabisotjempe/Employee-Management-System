package com.codewiththabi.employeemanagement.exception;

public class LeaveNotFoundException extends RuntimeException {

    public LeaveNotFoundException(Long id) {
        super("Leave request with id " + id + " was not found");
    }
}