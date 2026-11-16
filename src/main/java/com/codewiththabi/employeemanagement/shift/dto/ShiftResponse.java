package com.codewiththabi.employeemanagement.shift.dto;

import java.time.LocalTime;

public class ShiftResponse {

    private Long id;
    private String name;
    private LocalTime startTime;
    private LocalTime endTime;

    public ShiftResponse(
            Long id,
            String name,
            LocalTime startTime,
            LocalTime endTime
    ) {
        this.id = id;
        this.name = name;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public Long getId() {
        return id;
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
}