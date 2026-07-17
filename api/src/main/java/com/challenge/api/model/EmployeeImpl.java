package com.challenge.api.model;

import java.time.Instant;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;

// Concrete Employee implementation serialized to/from JSON by the REST layer; Lombok generates the getters/setters.
@Getter
@Setter
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
}
