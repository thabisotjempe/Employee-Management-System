package com.codewiththabi.employeemanagement.attendance.dto;

import com.codewiththabi.employeemanagement.attendance.entity.AttendanceStatus;

import java.time.LocalDate;
import java.time.LocalTime;

public class AttendanceResponse {

    private Long id;
    private LocalDate date;
    private LocalTime checkIn;
    private LocalTime checkOut;
    private AttendanceStatus status;

    public AttendanceResponse() {
    }
    public AttendanceResponse(
            Long id,
            LocalDate date,
            LocalTime checkIn,
            LocalTime checkOut,
            AttendanceStatus status
    ) {
        this.id = id;
        this.date = date;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public LocalDate getDate() {
        return date;
    }

    public LocalTime getCheckIn() {
        return checkIn;
    }

    public LocalTime getCheckOut() {
        return checkOut;
    }

    public AttendanceStatus getStatus() {
        return status;
    }
}
