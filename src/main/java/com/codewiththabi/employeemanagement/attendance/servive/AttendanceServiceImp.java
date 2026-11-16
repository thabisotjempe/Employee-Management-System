package com.codewiththabi.employeemanagement.attendance.servive;

import com.codewiththabi.employeemanagement.attendance.dto.AttendanceResponse;
import com.codewiththabi.employeemanagement.attendance.entity.Attendance;
import com.codewiththabi.employeemanagement.attendance.entity.AttendanceStatus;
import com.codewiththabi.employeemanagement.attendance.repository.AttendanceRepository;
import com.codewiththabi.employeemanagement.auth.entity.User;
import com.codewiththabi.employeemanagement.employee.entity.Employee;
import com.codewiththabi.employeemanagement.exception.AttendanceAlreadyExistsException;
import com.codewiththabi.employeemanagement.shift.entity.Shift;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
public class AttendanceServiceImp implements AttendanceService{

    private final AttendanceRepository attendanceRepository;

    public AttendanceServiceImp(
            AttendanceRepository attendanceRepository
    ) {
        this.attendanceRepository = attendanceRepository;
    }

    @Override
    public AttendanceResponse checkIn() {

        User user = (User) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();

        Employee employee = user.getEmployee();

        if (employee == null) {
            throw new RuntimeException(
                    "User is not linked to an employee"
            );
        }

        if (employee.getShift() == null) {
            throw new RuntimeException(
                    "Employee does not have a shift assigned"
            );
        }

        LocalDate today = LocalDate.now();

        if (attendanceRepository
                .findByEmployeeAndDate(employee, today)
                .isPresent()) {

            throw new AttendanceAlreadyExistsException();
        }

        LocalTime currentTime = LocalTime.now();

        Shift shift = employee.getShift();

        AttendanceStatus status;

        if (currentTime.isAfter(shift.getStartTime())) {
            status = AttendanceStatus.LATE;
        } else {
            status = AttendanceStatus.PRESENT;
        }

        Attendance attendance = new Attendance();

        attendance.setEmployee(employee);
        attendance.setDate(today);
        attendance.setCheckIn(currentTime);
        attendance.setStatus(status);

        Attendance savedAttendance =
                attendanceRepository.save(attendance);

        return toResponse(savedAttendance);
    }

    @Override
    public AttendanceResponse checkOut() {
        User user = (User) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();

        Employee employee = user.getEmployee();

        LocalDate today = LocalDate.now();

        Attendance attendance = attendanceRepository
                .findByEmployeeAndDate(employee, today)
                .orElseThrow(() ->
                        new AttendanceAlreadyExistsException()
                );

        if (attendance.getCheckOut() != null) {
            throw new RuntimeException(
                    "Employee has already checked out today"
            );
        }

        attendance.setCheckOut(LocalTime.now());

        Attendance savedAttendance =
                attendanceRepository.save(attendance);

        return toResponse(savedAttendance);
    }
    @Override
    public List<AttendanceResponse> getMyAttendance() {

        User user = (User) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();

        Employee employee = user.getEmployee();

        return attendanceRepository
                .findByEmployee(employee)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    private AttendanceResponse toResponse(Attendance attendance) {

        return new AttendanceResponse(
                attendance.getId(),
                attendance.getDate(),
                attendance.getCheckIn(),
                attendance.getCheckOut(),
                attendance.getStatus()
        );
    }
}
