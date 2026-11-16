package com.codewiththabi.employeemanagement.leave.service;

import com.codewiththabi.employeemanagement.auth.entity.User;
import com.codewiththabi.employeemanagement.employee.entity.Employee;
import com.codewiththabi.employeemanagement.exception.InvalidLeaveDateException;
import com.codewiththabi.employeemanagement.exception.InvalidLeaveStatusException;
import com.codewiththabi.employeemanagement.exception.LeaveNotFoundException;
import com.codewiththabi.employeemanagement.exception.UnauthorizedLeaveAccessException;
import com.codewiththabi.employeemanagement.leave.dto.LeaveRequest;
import com.codewiththabi.employeemanagement.leave.dto.LeaveResponse;
import com.codewiththabi.employeemanagement.leave.entity.Leave;
import com.codewiththabi.employeemanagement.leave.entity.LeaveStatus;
import com.codewiththabi.employeemanagement.leave.repository.LeaveRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LeaveServiceImp implements LeaveService {

    private final LeaveRepository leaveRepository;

    public LeaveServiceImp(
            LeaveRepository leaveRepository
    ) {
        this.leaveRepository = leaveRepository;
    }

    @Override
    public LeaveResponse requestLeave(
            LeaveRequest request
    ) {

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

        if (request.getEndDate()
                .isBefore(request.getStartDate())) {

            throw new InvalidLeaveDateException(
                    "End date cannot be before start date"
            );
        }

        Leave leave = new Leave();

        leave.setEmployee(employee);
        leave.setLeaveType(request.getLeaveType());
        leave.setStartDate(request.getStartDate());
        leave.setEndDate(request.getEndDate());
        leave.setReason(request.getReason());

        // Employee requests always start as PENDING
        leave.setStatus(LeaveStatus.PENDING);

        Leave savedLeave =
                leaveRepository.save(leave);

        return toResponse(savedLeave);
    }

    @Override
    public List<LeaveResponse> getMyLeaves() {

        User user = (User) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();

        Employee employee = user.getEmployee();

        return leaveRepository
                .findByEmployee(employee)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Override
    public LeaveResponse getMyLeave(Long id) {

        User user = (User) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();

        Employee employee = user.getEmployee();

        Leave leave = leaveRepository
                .findById(id)
                .orElseThrow(() ->
                        new LeaveNotFoundException(id)
                );

        if (!leave.getEmployee()
                .getId()
                .equals(employee.getId())) {

            throw new UnauthorizedLeaveAccessException();
        }

        return toResponse(leave);
    }

    @Override
    public void cancelLeave(Long id) {

        User user = (User) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();

        Employee employee = user.getEmployee();

        Leave leave = leaveRepository
                .findById(id)
                .orElseThrow(() ->
                        new LeaveNotFoundException(id)
                );

        if (!leave.getEmployee()
                .getId()
                .equals(employee.getId())) {

            throw new UnauthorizedLeaveAccessException();
        }

        if (leave.getStatus() != LeaveStatus.PENDING) {

            throw new InvalidLeaveStatusException(
                    "Only pending leave requests can be cancelled"
            );
        }

        leave.setStatus(LeaveStatus.CANCELLED);

        leaveRepository.save(leave);
    }

    @Override
    public List<LeaveResponse> getAllLeaves() {

        return leaveRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Override
    public LeaveResponse approveLeave(Long id) {

        Leave leave = leaveRepository.findById(id)
                .orElseThrow(() ->
                        new LeaveNotFoundException(id)
                );

        if (leave.getStatus() != LeaveStatus.PENDING) {

            throw new InvalidLeaveStatusException(
                    "Only pending leave requests can be approved"
            );
        }

        leave.setStatus(LeaveStatus.APPROVED);

        Leave updatedLeave =
                leaveRepository.save(leave);

        return toResponse(updatedLeave);
    }

    @Override
    public LeaveResponse rejectLeave(Long id) {

        Leave leave = leaveRepository.findById(id)
                .orElseThrow(() ->
                        new LeaveNotFoundException(id)
                );

        if (leave.getStatus() != LeaveStatus.PENDING) {

            throw new InvalidLeaveStatusException(
                    "Only pending leave requests can be rejected"
            );
        }

        leave.setStatus(LeaveStatus.REJECTED);

        Leave updatedLeave =
                leaveRepository.save(leave);

        return toResponse(updatedLeave);
    }

    private LeaveResponse toResponse(
            Leave leave
    ) {

        return new LeaveResponse(
                leave.getId(),
                leave.getLeaveType(),
                leave.getStartDate(),
                leave.getEndDate(),
                leave.getReason(),
                leave.getStatus()
        );
    }
}