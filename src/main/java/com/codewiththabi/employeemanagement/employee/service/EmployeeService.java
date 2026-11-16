package com.codewiththabi.employeemanagement.employee.service;



import com.codewiththabi.employeemanagement.employee.dto.EmployeeRequest;
import com.codewiththabi.employeemanagement.employee.dto.EmployeeResponse;

import java.util.List;

public interface EmployeeService {

    List<EmployeeResponse> getAllEmployees();
    EmployeeResponse getEmployeeById(Long id);
    EmployeeResponse createEmployee(EmployeeRequest request);
    EmployeeResponse updateEmployee(Long id, EmployeeRequest request);
    void deleteEmployee(Long id);
    EmployeeResponse assignShift(
            Long employeeId,
            Long shiftId
    );

}
