package com.codewiththabi.employeemanagement.leave.service;

import com.codewiththabi.employeemanagement.leave.dto.LeaveRequest;
import com.codewiththabi.employeemanagement.leave.dto.LeaveResponse;

import java.util.List;

public interface LeaveService {

    // Employee
    LeaveResponse requestLeave(LeaveRequest request);

    List<LeaveResponse> getMyLeaves();

    LeaveResponse getMyLeave(Long id);

    void cancelLeave(Long id);

    // Admin
    List<LeaveResponse> getAllLeaves();

    LeaveResponse approveLeave(Long id);

    LeaveResponse rejectLeave(Long id);
}