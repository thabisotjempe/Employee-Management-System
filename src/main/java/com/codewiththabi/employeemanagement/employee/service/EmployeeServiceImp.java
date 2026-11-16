package com.codewiththabi.employeemanagement.employee.service;


import com.codewiththabi.employeemanagement.employee.dto.EmployeeRequest;
import com.codewiththabi.employeemanagement.employee.dto.EmployeeResponse;
import com.codewiththabi.employeemanagement.employee.entity.Employee;
import com.codewiththabi.employeemanagement.employee.entity.EmployeeStatus;
import com.codewiththabi.employeemanagement.employee.repository.EmployeeRepository;
import com.codewiththabi.employeemanagement.exception.EmailAlreadyExistsException;
import com.codewiththabi.employeemanagement.exception.EmployeeNotFoundException;

import com.codewiththabi.employeemanagement.shift.entity.Shift;
import com.codewiththabi.employeemanagement.shift.repository.ShiftRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class EmployeeServiceImp implements EmployeeService{


    private final EmployeeRepository employeeRepository;
    private final ShiftRepository shiftRepository;

    public EmployeeServiceImp(
            EmployeeRepository employeeRepository,
            ShiftRepository shiftRepository
    ) {
        this.employeeRepository = employeeRepository;
        this.shiftRepository = shiftRepository;
    }


    @Override
    public List<EmployeeResponse> getAllEmployees() {
        return employeeRepository.findAll()
                .stream()
                .map(this::convertToResponse)
                .toList();
    }

    @Override
    public EmployeeResponse getEmployeeById(Long id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() ->
                        new EmployeeNotFoundException(id));

        return convertToResponse(employee);
    }

    @Override
    public EmployeeResponse createEmployee(EmployeeRequest request) {
        if (employeeRepository.existsByEmail(request.getEmail())) {
            throw new EmailAlreadyExistsException(request.getEmail());
        }

        Employee employee = new Employee();

        employee.setFirstName(request.getFirstName());
        employee.setLastName(request.getLastName());
        employee.setEmail(request.getEmail());
        employee.setPhone(request.getPhone());
        employee.setPosition(request.getPosition());

        employee.setDateHired(LocalDate.now());
        employee.setStatus(EmployeeStatus.ACTIVE);

        Employee savedEmployee =
                employeeRepository.save(employee);

        return convertToResponse(savedEmployee);
    }

    @Override
    public EmployeeResponse updateEmployee(Long id, EmployeeRequest request) {


        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() ->
                        new EmployeeNotFoundException((id)));

        if (!employee.getEmail().equals(request.getEmail())
                && employeeRepository.existsByEmail(request.getEmail())) {

            throw new EmailAlreadyExistsException(request.getEmail());
        }

        employee.setFirstName(request.getFirstName());
        employee.setLastName(request.getLastName());
        employee.setEmail(request.getEmail());
        employee.setPhone(request.getPhone());
        employee.setPosition(request.getPosition());

        Employee updatedEmployee = employeeRepository.save(employee);
        return convertToResponse(updatedEmployee);
    }

    @Override
    public void deleteEmployee(Long id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() ->
                        new EmployeeNotFoundException((id)));
        employeeRepository.delete(employee);
    }

    @Override
    public EmployeeResponse assignShift(
            Long employeeId,
            Long shiftId
    ) {

        Employee employee = employeeRepository
                .findById(employeeId)
                .orElseThrow(() ->
                        new EmployeeNotFoundException(employeeId)
                );

        Shift shift = shiftRepository
                .findById(shiftId)
                .orElseThrow(() ->
                        new RuntimeException("Shift not found")
                );

        employee.setShift(shift);

        Employee updatedEmployee =
                employeeRepository.save(employee);

        return convertToResponse(updatedEmployee);
    }

    private EmployeeResponse convertToResponse(
            Employee employee) {

        EmployeeResponse response =
                new EmployeeResponse();

        response.setId(employee.getId());
        response.setFirstName(employee.getFirstName());
        response.setLastName(employee.getLastName());
        response.setEmail(employee.getEmail());
        response.setPhone(employee.getPhone());
        response.setPosition(employee.getPosition());
        response.setDateHired(employee.getDateHired());
        response.setStatus(employee.getStatus());

        return response;
    }
}
