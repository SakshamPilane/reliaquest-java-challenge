package com.challenge.api.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

// Request body for creating/updating an employee; validated before it reaches the service. Lombok generates accessors.
@Getter
@Setter
public class CreateEmployeeRequest {

    @NotBlank(message = "firstName is required")
    private String firstName;

    @NotBlank(message = "lastName is required")
    private String lastName;

    @NotNull(message = "salary is required") private Integer salary;

    @NotNull(message = "age is required") private Integer age;

    @NotBlank(message = "jobTitle is required")
    private String jobTitle;

    @NotBlank(message = "email is required")
    @Email(message = "email must be a valid email address")
    private String email;
}
