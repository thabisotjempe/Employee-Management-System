package com.codewiththabi.employeemanagement.attendance.controller;

import com.codewiththabi.employeemanagement.attendance.dto.AttendanceResponse;
import com.codewiththabi.employeemanagement.attendance.entity.Attendance;
import com.codewiththabi.employeemanagement.attendance.servive.AttendanceService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/attendance")
public class AttendanceController {
    private final AttendanceService attendanceService;

    public AttendanceController(
            AttendanceService attendanceService
    ) {
        this.attendanceService = attendanceService;
    }

    @PostMapping("/check-in")
    public ResponseEntity<AttendanceResponse> checkIn() {

        AttendanceResponse attendance =
                attendanceService.checkIn();

        return ResponseEntity.ok(attendance);
    }
    @PostMapping("/check-out")
    public ResponseEntity<AttendanceResponse> checkOut() {

        AttendanceResponse attendance =
                attendanceService.checkOut();

        return ResponseEntity.ok(attendance);
    }

    @GetMapping("/me")
    public ResponseEntity<List<AttendanceResponse>> getMyAttendance() {

        return ResponseEntity.ok(
                attendanceService.getMyAttendance()
        );
    }
}
