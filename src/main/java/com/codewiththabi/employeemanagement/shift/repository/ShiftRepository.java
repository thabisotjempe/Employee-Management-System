package com.codewiththabi.employeemanagement.shift.repository;

import com.codewiththabi.employeemanagement.shift.entity.Shift;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShiftRepository extends JpaRepository<Shift, Long> {
}
