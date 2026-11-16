package com.codewiththabi.employeemanagement.shift.service;

import com.codewiththabi.employeemanagement.shift.dto.ShiftRequest;
import com.codewiththabi.employeemanagement.shift.dto.ShiftResponse;

import java.util.List;

public interface ShiftService {

    ShiftResponse createShift(ShiftRequest request);

    List<ShiftResponse> getAllShifts();

    ShiftResponse getShift(Long id);

    ShiftResponse updateShift(Long id, ShiftRequest request);

    void deleteShift(Long id);
}