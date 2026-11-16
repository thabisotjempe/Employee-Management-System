package com.codewiththabi.employeemanagement.leave.repository;

import com.codewiththabi.employeemanagement.employee.entity.Employee;
import com.codewiththabi.employeemanagement.leave.entity.Leave;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LeaveRepository
        extends JpaRepository<Leave, Long> {

    List<Leave> findByEmployee(Employee employee);
}