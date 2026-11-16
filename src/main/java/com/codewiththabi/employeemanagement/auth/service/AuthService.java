package com.codewiththabi.employeemanagement.auth.service;

import com.codewiththabi.employeemanagement.auth.dto.LoginRequest;
import com.codewiththabi.employeemanagement.auth.dto.LoginResponse;
import com.codewiththabi.employeemanagement.auth.dto.RegisterRequest;

public interface AuthService {

    void register(RegisterRequest request);

    LoginResponse login(LoginRequest request);
}
