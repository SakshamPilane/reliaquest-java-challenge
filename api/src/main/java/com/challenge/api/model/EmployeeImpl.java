package com.challenge.api.model;

import java.time.Instant;
import java.util.UUID;

// Concrete Employee implementation serialized to/from JSON by the REST layer.
public class EmployeeImpl implements Employee {

    private UUID uuid;
    private String firstName;
    private String lastName;
    private String fullName;
    private Integer salary;
    private Integer age;
    private String jobTitle;
    private String email;
    private Instant contractHireDate;
    private Instant contractTerminationDate;

    @Override
    public UUID getUuid() {
        return uuid;
    }

    @Override
    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    @Override
    public String getFirstName() {
        return firstName;
    }

    @Override
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Override
    public String getLastName() {
        return lastName;
    }

    @Override
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    // Returns the explicitly set full name, otherwise one derived from first and last name.
    @Override
    public String getFullName() {
        if (fullName != null && !fullName.isBlank()) {
            return fullName;
        }
        if (firstName == null && lastName == null) {
            return null;
        }
        return String.join(" ", firstName == null ? "" : firstName, lastName == null ? "" : lastName)
                .trim();
    }

    @Override
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    @Override
    public Integer getSalary() {
        return salary;
    }

    @Override
    public void setSalary(Integer salary) {
        this.salary = salary;
    }

    @Override
    public Integer getAge() {
        return age;
    }

    @Override
    public void setAge(Integer age) {
        this.age = age;
    }

    @Override
    public String getJobTitle() {
        return jobTitle;
    }

    @Override
    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    @Override
    public String getEmail() {
        return email;
    }

    @Override
    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public Instant getContractHireDate() {
        return contractHireDate;
    }

    @Override
    public void setContractHireDate(Instant contractHireDate) {
        this.contractHireDate = contractHireDate;
    }

    @Override
    public Instant getContractTerminationDate() {
        return contractTerminationDate;
    }

    @Override
    public void setContractTerminationDate(Instant contractTerminationDate) {
        this.contractTerminationDate = contractTerminationDate;
    }
}
