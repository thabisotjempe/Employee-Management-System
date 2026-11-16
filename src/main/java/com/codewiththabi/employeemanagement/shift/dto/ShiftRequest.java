package com.codewiththabi.employeemanagement.shift.dto;

import java.time.LocalTime;

public class ShiftRequest {

    private String name;
    private LocalTime startTime;
    private LocalTime endTime;

    public ShiftRequest() {
    }

    public String getName() {
        return name;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }
}