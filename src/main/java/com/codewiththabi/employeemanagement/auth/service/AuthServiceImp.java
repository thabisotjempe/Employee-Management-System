package com.codewiththabi.employeemanagement.auth.service;

import com.codewiththabi.employeemanagement.auth.service.JwtService;
import com.codewiththabi.employeemanagement.auth.dto.LoginRequest;
import com.codewiththabi.employeemanagement.auth.dto.LoginResponse;
import com.codewiththabi.employeemanagement.auth.dto.RegisterRequest;
import com.codewiththabi.employeemanagement.auth.entity.Role;
import com.codewiththabi.employeemanagement.auth.entity.User;
import com.codewiththabi.employeemanagement.auth.repository.UserRepository;
import com.codewiththabi.employeemanagement.employee.entity.Employee;
import com.codewiththabi.employeemanagement.employee.entity.EmployeeStatus;
import com.codewiththabi.employeemanagement.employee.repository.EmployeeRepository;
import com.codewiththabi.employeemanagement.exception.EmailAlreadyExistsException;
import com.codewiththabi.employeemanagement.exception.InvalidCredentialsException;
import jakarta.transaction.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class AuthServiceImp implements AuthService{


    private final UserRepository userRepository;
    private final EmployeeRepository employeeRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthServiceImp(UserRepository userRepository, EmployeeRepository employeeRepository, PasswordEncoder passwordEncoder, JwtService jwtService) {
        this.userRepository = userRepository;
        this.employeeRepository = employeeRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    @Override
    @Transactional
    public void register(RegisterRequest request) {

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new EmailAlreadyExistsException(
                    "Email already registered"
            );
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

        User user = new User();

        user.setEmail(request.getEmail());

        user.setPassword(
                passwordEncoder.encode(
                        request.getPassword()
                )
        );

        user.setRole(Role.EMPLOYEE);
        user.setEmployee(savedEmployee);
        userRepository.save(user);
    }

    @Override
    public LoginResponse login(LoginRequest request) {

        User user = userRepository
                .findByEmail(request.getEmail())
                .orElseThrow(() ->
                        new InvalidCredentialsException(
                                "Invalid email or password"
                        )
                );
        if (!passwordEncoder.matches(
                request.getPassword(),
                user.getPassword()
        )) {

            throw new InvalidCredentialsException(
                    "Invalid email or password"
            );
        }
        String token = jwtService.generateToken(user);

        return new LoginResponse(token);

    }
}
