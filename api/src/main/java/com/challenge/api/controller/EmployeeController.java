package com.challenge.api.controller;

import com.challenge.api.dto.CreateEmployeeRequest;
import com.challenge.api.model.Employee;
import com.challenge.api.service.EmployeeService;
import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

// REST controller exposing employee endpoints; delegates all logic to EmployeeService.
@RestController
@RequestMapping("/api/v1/employee")
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    // Returns all employees, unfiltered.
    @GetMapping
    public List<Employee> getAllEmployees() {
        return employeeService.getAllEmployees();
    }

    // Returns the employee for the given UUID, or 404 if not found.
    @GetMapping("/{uuid}")
    public Employee getEmployeeByUuid(@PathVariable UUID uuid) {
        Employee employee = employeeService.getEmployeeByUuid(uuid);
        if (employee == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Employee not found: " + uuid);
        }
        return employee;
    }

    // Creates a new employee from the request body and returns it.
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Employee createEmployee(@Valid @RequestBody CreateEmployeeRequest requestBody) {
        return employeeService.createEmployee(requestBody);
    }

    // Updates the employee for the given UUID, or 404 if not found.
    @PutMapping("/{uuid}")
    public Employee updateEmployee(@PathVariable UUID uuid, @Valid @RequestBody CreateEmployeeRequest requestBody) {
        Employee updated = employeeService.updateEmployee(uuid, requestBody);
        if (updated == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Employee not found: " + uuid);
        }
        return updated;
    }

    // Deletes the employee for the given UUID and returns it, or 404 if not found.
    @DeleteMapping("/{uuid}")
    public Employee deleteEmployee(@PathVariable UUID uuid) {
        Employee deleted = employeeService.deleteEmployee(uuid);
        if (deleted == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Employee not found: " + uuid);
        }
        return deleted;
    }
}
