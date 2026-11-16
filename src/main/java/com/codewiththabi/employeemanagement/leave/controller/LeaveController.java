package com.codewiththabi.employeemanagement.leave.controller;

import com.codewiththabi.employeemanagement.leave.dto.LeaveRequest;
import com.codewiththabi.employeemanagement.leave.dto.LeaveResponse;
import com.codewiththabi.employeemanagement.leave.service.LeaveService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/leaves")
public class LeaveController {

    private final LeaveService leaveService;

    public LeaveController(
            LeaveService leaveService
    ) {
        this.leaveService = leaveService;
    }

    @PostMapping
    public ResponseEntity<LeaveResponse> requestLeave(
            @Valid @RequestBody LeaveRequest request
    ) {

        return ResponseEntity.ok(
                leaveService.requestLeave(request)
        );
    }

    @GetMapping("/me")
    public ResponseEntity<List<LeaveResponse>> getMyLeaves() {

        return ResponseEntity.ok(
                leaveService.getMyLeaves()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<LeaveResponse> getMyLeave(
            @PathVariable Long id
    ) {

        return ResponseEntity.ok(
                leaveService.getMyLeave(id)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> cancelLeave(
            @PathVariable Long id
    ) {

        leaveService.cancelLeave(id);

        return ResponseEntity.noContent().build();
    }
}