package com.codewiththabi.employeemanagement.shift.controller;

import com.codewiththabi.employeemanagement.shift.dto.ShiftRequest;
import com.codewiththabi.employeemanagement.shift.dto.ShiftResponse;
import com.codewiththabi.employeemanagement.shift.service.ShiftService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/shifts")
public class ShiftController {

    private final ShiftService shiftService;

    public ShiftController(ShiftService shiftService) {
        this.shiftService = shiftService;
    }

    @PostMapping
    public ResponseEntity<ShiftResponse> createShift(
            @RequestBody ShiftRequest request
    ) {

        return ResponseEntity.ok(
                shiftService.createShift(request)
        );
    }

    @GetMapping
    public ResponseEntity<List<ShiftResponse>> getAllShifts() {

        return ResponseEntity.ok(
                shiftService.getAllShifts()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ShiftResponse> getShift(
            @PathVariable Long id
    ) {

        return ResponseEntity.ok(
                shiftService.getShift(id)
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<ShiftResponse> updateShift(
            @PathVariable Long id,
            @RequestBody ShiftRequest request
    ) {

        return ResponseEntity.ok(
                shiftService.updateShift(id, request)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteShift(
            @PathVariable Long id
    ) {

        shiftService.deleteShift(id);

        return ResponseEntity.noContent().build();
    }
}