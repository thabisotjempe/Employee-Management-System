package com.codewiththabi.employeemanagement.attendance.repository;

import com.codewiththabi.employeemanagement.attendance.entity.Attendance;
import com.codewiththabi.employeemanagement.employee.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface AttendanceRepository extends JpaRepository<Attendance, Long> {
    Optional<Attendance> findByEmployeeAndDate(
            Employee employee,
            LocalDate date
    );
    List<Attendance> findByEmployee(Employee employee);
}
