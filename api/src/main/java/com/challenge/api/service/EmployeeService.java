package com.challenge.api.service;

import com.challenge.api.dto.CreateEmployeeRequest;
import com.challenge.api.model.Employee;
import com.challenge.api.model.EmployeeImpl;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class EmployeeService {

    private final Map<UUID, Employee> employees = new ConcurrentHashMap<>();

    public EmployeeService() {
        seedMockEmployees();
    }

    /**
     * @return every employee currently held, unfiltered.
     */
    public List<Employee> getAllEmployees() {
        return new ArrayList<>(employees.values());
    }

    /**
     * @param uuid employee identifier
     * @return the matching employee, or {@code null} if none exists.
     */
    public Employee getEmployeeByUuid(UUID uuid) {
        return employees.get(uuid);
    }

    /**
     * Creates and stores a new employee from the supplied request. Server-managed fields (UUID, hire date) are assigned
     * here rather than trusted from the caller.
     *
     * @param request attributes for the new employee
     * @return the persisted employee
     */
    public Employee createEmployee(CreateEmployeeRequest request) {
        requireEmailNotTaken(request.getEmail(), null);

        EmployeeImpl employee = new EmployeeImpl();
        employee.setUuid(UUID.randomUUID());
        employee.setFirstName(request.getFirstName());
        employee.setLastName(request.getLastName());
        employee.setSalary(request.getSalary());
        employee.setAge(request.getAge());
        employee.setJobTitle(request.getJobTitle());
        employee.setEmail(request.getEmail());
        employee.setContractHireDate(Instant.now());

        employees.put(employee.getUuid(), employee);
        return employee;
    }

    /**
     * Fully replaces the mutable attributes of an existing employee. Server-managed fields (UUID, hire date, termination
     * date) are preserved.
     *
     * @param uuid employee identifier
     * @param request new attribute values
     * @return the updated employee, or {@code null} if none exists for the given UUID.
     */
    public Employee updateEmployee(UUID uuid, CreateEmployeeRequest request) {
        Employee existing = employees.get(uuid);
        if (existing == null) {
            return null;
        }
        requireEmailNotTaken(request.getEmail(), uuid);
        existing.setFirstName(request.getFirstName());
        existing.setLastName(request.getLastName());
        existing.setSalary(request.getSalary());
        existing.setAge(request.getAge());
        existing.setJobTitle(request.getJobTitle());
        existing.setEmail(request.getEmail());
        return existing;
    }

    /**
     * Removes an employee from the store.
     *
     * @param uuid employee identifier
     * @return the removed employee, or {@code null} if none existed for the given UUID.
     */
    public Employee deleteEmployee(UUID uuid) {
        return employees.remove(uuid);
    }

    /**
     * Guards the email uniqueness constraint. An email may only belong to a single employee.
     *
     * @param email the email to validate
     * @param excludeUuid an employee to ignore during the check (the one being updated), or {@code null} on create
     * @throws ResponseStatusException 409 Conflict if the email is already used by a different employee
     */
    private void requireEmailNotTaken(String email, UUID excludeUuid) {
        if (email == null) {
            return;
        }
        boolean taken = employees.values().stream()
                .filter(existing -> !existing.getUuid().equals(excludeUuid))
                .anyMatch(existing -> email.equalsIgnoreCase(existing.getEmail()));
        if (taken) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Employee already exists with email: " + email);
        }
    }

    private void seedMockEmployees() {
        store(mockEmployee("Ada", "Lovelace", 145000, 36, "Principal Engineer", "ada.lovelace@example.com"));
        store(mockEmployee("Alan", "Turing", 152000, 41, "Engineering Lead", "alan.turing@example.com"));
        store(mockEmployee("Grace", "Hopper", 138000, 45, "Staff Engineer", "grace.hopper@example.com"));
    }

    private void store(Employee employee) {
        employees.put(employee.getUuid(), employee);
    }

    private Employee mockEmployee(
            String firstName, String lastName, Integer salary, Integer age, String jobTitle, String email) {
        EmployeeImpl employee = new EmployeeImpl();
        employee.setUuid(UUID.randomUUID());
        employee.setFirstName(firstName);
        employee.setLastName(lastName);
        employee.setSalary(salary);
        employee.setAge(age);
        employee.setJobTitle(jobTitle);
        employee.setEmail(email);
        employee.setContractHireDate(Instant.now());
        return employee;
    }
}
