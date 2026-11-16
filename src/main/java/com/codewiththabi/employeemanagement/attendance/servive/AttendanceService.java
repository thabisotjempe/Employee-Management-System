package com.codewiththabi.employeemanagement.attendance.servive;

import com.codewiththabi.employeemanagement.attendance.dto.AttendanceResponse;
import com.codewiththabi.employeemanagement.attendance.entity.Attendance;

import java.util.List;

public interface AttendanceService {

    AttendanceResponse checkIn();

    AttendanceResponse checkOut();

    List<AttendanceResponse> getMyAttendance();
}
