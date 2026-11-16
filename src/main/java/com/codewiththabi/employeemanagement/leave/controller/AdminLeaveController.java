package com.codewiththabi.employeemanagement.leave.controller;

import com.codewiththabi.employeemanagement.leave.dto.LeaveResponse;
import com.codewiththabi.employeemanagement.leave.service.LeaveService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/leaves")
public class AdminLeaveController {

    private final LeaveService leaveService;

    public AdminLeaveController(
            LeaveService leaveService
    ) {
        this.leaveService = leaveService;
    }

    @GetMapping
    public ResponseEntity<List<LeaveResponse>> getAllLeaves() {

        return ResponseEntity.ok(
                leaveService.getAllLeaves()
        );
    }

    @PutMapping("/{id}/approve")
    public ResponseEntity<LeaveResponse> approveLeave(
            @PathVariable Long id
    ) {

        return ResponseEntity.ok(
                leaveService.approveLeave(id)
        );
    }

    @PutMapping("/{id}/reject")
    public ResponseEntity<LeaveResponse> rejectLeave(
            @PathVariable Long id
    ) {

        return ResponseEntity.ok(
                leaveService.rejectLeave(id)
        );
    }
}