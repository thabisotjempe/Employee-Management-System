package com.codewiththabi.employeemanagement.shift.service;

import com.codewiththabi.employeemanagement.shift.dto.ShiftRequest;
import com.codewiththabi.employeemanagement.shift.dto.ShiftResponse;
import com.codewiththabi.employeemanagement.shift.entity.Shift;
import com.codewiththabi.employeemanagement.shift.repository.ShiftRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShiftServiceImp implements ShiftService {

    private final ShiftRepository shiftRepository;

    public ShiftServiceImp(ShiftRepository shiftRepository) {
        this.shiftRepository = shiftRepository;
    }

    @Override
    public ShiftResponse createShift(ShiftRequest request) {

        Shift shift = new Shift();

        shift.setName(request.getName());
        shift.setStartTime(request.getStartTime());
        shift.setEndTime(request.getEndTime());

        Shift savedShift = shiftRepository.save(shift);

        return toResponse(savedShift);
    }

    @Override
    public List<ShiftResponse> getAllShifts() {

        return shiftRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Override
    public ShiftResponse getShift(Long id) {

        Shift shift = shiftRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Shift not found"
                        )
                );

        return toResponse(shift);
    }

    @Override
    public ShiftResponse updateShift(
            Long id,
            ShiftRequest request
    ) {

        Shift shift = shiftRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Shift not found"
                        )
                );

        shift.setName(request.getName());
        shift.setStartTime(request.getStartTime());
        shift.setEndTime(request.getEndTime());

        Shift updatedShift = shiftRepository.save(shift);

        return toResponse(updatedShift);
    }

    @Override
    public void deleteShift(Long id) {

        Shift shift = shiftRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Shift not found"
                        )
                );

        shiftRepository.delete(shift);
    }

    private ShiftResponse toResponse(Shift shift) {

        return new ShiftResponse(
                shift.getId(),
                shift.getName(),
                shift.getStartTime(),
                shift.getEndTime()
        );
    }
}