# ReliaQuest's Entry-Level Java Challenge

Please keep the following in mind while working on this challenge:
* Code implementations will not be graded for **correctness** but rather on practicality
* Articulate clear and concise design methodologies, if necessary
* Use clean coding etiquette
  * E.g. avoid liberal use of new-lines, odd variable and method names, random indentation, etc...
* Test cases are not required

## Problem Statement

Your employer has recently purchased a license to top-tier SaaS platform, Employees-R-US, to off-load all employee management responsibilities.
Unfortunately, your company's product has an existing employee management solution that is tightly coupled to other services and therefore 
cannot be replaced whole-cloth. Product and Development leads in your department have decided it would be best to interface
the existing employee management solution with the commercial offering from Employees-R-US for the time being until all employees can be
migrated to the new SaaS platform.

Your ask is to expose employee information as a protected, secure REST API for consumption by Employees-R-US web hooks.
The initial REST API will consist of 3 endpoints, listed in the following section. If for any reason the implementation 
of an endpoint is problematic, the team lead will accept **pseudo-code** and a pertinent description (e.g. java-doc) of intent.

Good luck!

## Endpoints to implement (API module)

_See `com.challenge.api.controller.EmployeeController` for details._

getAllEmployees()

    output - list of employees
    description - this should return all employees, unfiltered

getEmployeeByUuid(...)

    path variable - employee UUID
    output - employee
    description - this should return a single employee based on the provided employee UUID

createEmployee(...)

    request body - attributes necessary to create an employee
    output - employee
    description - this should return a single employee, if created, otherwise error

## Code Formatting

This project utilizes Gradle plugin [Diffplug Spotless](https://github.com/diffplug/spotless/tree/main/plugin-gradle) to enforce format
and style guidelines with every build.

To format code according to style guidelines, you can run **spotlessApply** task.
`./gradlew spotlessApply`

The spotless plugin will also execute check-and-validation tasks as part of the gradle **build** task.
`./gradlew build`

---

## My Implementation

### How I approached it

I started with the three endpoints the brief asked for, got them working end to end, and then added update and
delete so the API is a full CRUD resource rather than a partial one. The brief said not to worry about a real
persistence layer, so I kept the data in memory behind a service and seeded it with a few sample employees. That
way the project runs straight away, and the "database" question stays out of scope like the challenge intended.

### How the code is laid out

I kept the layers separate so each class has one job and the flow is easy to follow:

- `controller/EmployeeController` — takes the HTTP requests and hands them to the service.
- `service/EmployeeService` — where the actual logic lives, plus the in-memory store.
- `model/Employee` and `model/EmployeeImpl` — the interface I was given and the concrete class behind it.
- `dto/CreateEmployeeRequest` — the shape of the JSON sent in on create and update.
- `exception/GlobalExceptionHandler` + `exception/ErrorResponse` — one place that turns any error into a tidy JSON body.

One thing I like about this split: the controller never touches the store directly, so if this ever needed a real
database, I'd only have to change the service — nothing else would notice.

### The endpoints

| Method | Path | What it does | Success |
| --- | --- | --- | --- |
| GET | `/api/v1/employee` | Get every employee | 200 |
| GET | `/api/v1/employee/{uuid}` | Get one employee by UUID | 200 (404 if it isn't there) |
| POST | `/api/v1/employee` | Create a new employee | 201 |
| PUT | `/api/v1/employee/{uuid}` | Update an existing employee | 200 (404 if it isn't there) |
| DELETE | `/api/v1/employee/{uuid}` | Delete an employee and return it | 200 (404 if it isn't there) |

Sample body for create/update:

```json
{
  "firstName": "Michael",
  "lastName": "Scott",
  "salary": 120000,
  "age": 45,
  "jobTitle": "Regional Manager",
  "email": "michael.scott@example.com"
}
```

### A few extras I chose to add

None of these were strictly required, but they felt like the sort of thing you'd want on a real API:

- **Validation on the way in.** I check that the required fields are present and that the email actually looks like
  an email. If something's off, the request is turned away with a `400` and a message saying what was wrong, so bad
  data never reaches the service.
- **No duplicate emails.** An email should belong to one person, so creating or updating to an email that's already
  taken comes back as a `409 Conflict` rather than quietly creating a second copy.
- **Errors that all look the same.** Instead of leaking a stack trace, every failure returns the same small JSON
  shape (timestamp, status, error, message, path), which is much friendlier for whoever's calling the API.
- **Lombok for the boring bits.** I used `@Getter`/`@Setter` on the model and request classes so they aren't buried
  under getters and setters. I'd hand-written those first (they're in the earlier commits) before switching over.

