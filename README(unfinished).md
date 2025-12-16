# Bus Station Management System
### Enum Validation, Clean Architecture, and Robust Error Handling in Spring MVC

---

## Overview

This project demonstrates a **professional, maintainable Spring Boot MVC application** with a strong focus on **explicit enum validation**, **predictable error handling**, and **clean backend architecture**.

A key design goal of this system is to **avoid implicit framework behavior** and instead implement **domain-driven validation**, ensuring that invalid user input is handled consistently and transparently, with clear feedback provided to the end user.

---

## Enum Validation in Spring MVC (Thymeleaf Forms)

In this project, enum validation (for example `BusStatus`) is handled **explicitly** because **Spring MVC performs data binding and type conversion before controller methods are executed**.

When a form field is directly mapped to an enum type and the submitted value does not match any enum constant, **Spring throws an exception before the controller method is reached**.

This behavior is part of the **Spring MVC binding lifecycle** and is **independent of**:
- `@Valid`
- `@BindingResult`
- the presence or absence of a JPA entity

As a result, invalid enum values cannot be intercepted using standard Bean Validation alone.

---

## Why `@Valid` and `BindingResult` Are Not Enough

The `@Valid` annotation is executed **after** data binding and type conversion.

Because enum conversion errors occur **earlier** in the request lifecycle, they:
- cannot be captured by `BindingResult`
- result in exceptions such as `IllegalArgumentException` or `MethodArgumentTypeMismatchException`
- bypass controller-level validation logic entirely

Therefore, **Bean Validation alone cannot prevent enum conversion failures** when invalid values are submitted from a form.

---

## Chosen Approach: String Field + Manual Enum Validation

To maintain full control over validation and enable proper error handling in Thymeleaf forms, the project uses a **form object (DTO)** where enum-related fields are defined as `String` instead of enum types.

Conversion and validation are performed explicitly using a static factory method (e.g. `BusStatus.fromString()`), which:

- accepts case-insensitive input (`DOWN`, `down`, `Down`)
- supports both enum names and human-readable descriptions
- throws a controlled custom exception (`InvalidBusStatusException`) for invalid values

This approach allows validation errors to be handled gracefully and displayed directly in the UI, without exposing technical framework errors.

---

## Separation of Responsibilities

The application follows a strict separation of concerns:

- **Form Object / DTO**  
  Receives raw user input (`String` values)

- **Enum**  
  Contains validation and conversion logic

- **Controller**  
  Orchestrates request flow and delegates validation

- **Business Logic / Model Layer**  
  Receives only validated enum values (`BusStatus`)

By centralizing conversion logic, the application avoids implicit framework behavior and remains predictable and maintainable.

---

## Why Direct Enum Binding Was Avoided

Directly binding form fields to enum types was intentionally avoided because it:

- causes exceptions before the controller is invoked
- prevents the use of `BindingResult`
- makes user-friendly error messages difficult to implement
- reduces control over accepted input formats

Using a `String` field combined with explicit enum validation results in a **more robust and maintainable solution**.

---

## Validation and Exception Handling Design

This application uses a **domain-driven validation approach**, where business rules are enforced at the **model level**, rather than relying on Spring’s automatic binding and conversion mechanisms.

The objective is to ensure that:
- invalid user input is handled consistently
- application behavior remains predictable
- error messages are clear and domain-specific

---

## Manual Enum Validation Strategy

Enum values (such as `BusStatus`) are validated manually rather than using Spring MVC’s automatic enum conversion.

Instead of allowing Spring to convert request parameters directly into enum values:
- enum fields are set via `String` setters
- inside these setters, input is explicitly converted using `Enum.valueOf()` or a custom factory method
- if conversion fails, a custom domain exception is thrown

This approach provides:
- full control over enum validation logic
- clear, domain-specific error messages
- independence from Spring converters and configuration

---

## Why Enum Setters Accept `String`

Spring MVC selects setters based on the **field type**, not the method signature.

If a setter that accepts the enum type exists, Spring will always attempt automatic conversion **before invoking any custom logic**.

To prevent this behavior:
- setters accepting enum types are intentionally avoided
- only `String` setters are used for enum-backed fields

This guarantees that:
- Spring does not perform automatic enum conversion
- validation logic always executes inside the model
- custom exceptions are reliably thrown and handled

---

## Centralized Exception Handling

All exceptions are handled through a single `@ControllerAdvice` class (`GlobalExceptionHandler`).

This handler is responsible for:
- handling domain-specific exceptions (e.g. duplicate entries, invalid values)
- handling binding-related errors (`MethodArgumentNotValidException`)
- extracting meaningful domain error messages
- preventing exposure of framework internals

As a result, users never see technical stack traces or binding error messages.

---

## Handling Binding Errors (`MethodArgumentNotValidException`)

Binding errors can originate from multiple sources, including:
- invalid enum values
- invalid numeric ranges
- incorrect form input formats

The global exception handler:
- inspects the most specific underlying cause
- displays the custom exception message when present
- falls back to standard validation messages when appropriate

This makes the handler **generic, reusable, and future-proof**, capable of supporting new models without model-specific logic.

---

## Scalability and Maintainability

This validation architecture is designed to scale naturally:

- new enums can be added without configuration changes
- new validation rules require only model-level updates
- the global exception handler automatically supports new domain exceptions

By centralizing validation and exception handling, the application remains:
- maintainable
- predictable
- easy to extend as the domain grows

---

## Project Overview

This project is a **Bus Station Management System** developed using:
- Spring Boot
- Spring Data JPA
- Thymeleaf

It models a real-world transportation domain and provides functionality for managing:
- bus stations
- routes
- trips
- tickets
- passengers
- staff

The primary objective is to demonstrate **clean backend architecture**, **relational data modeling**, and **server-side rendered views** using modern Java technologies.

---

## Architecture & Technologies

The application follows the **Model–View–Controller (MVC)** architectural pattern:

- **Model** – JPA entities representing the domain
- **View** – Thymeleaf templates for dynamic HTML rendering
- **Controller** – Spring MVC controllers handling HTTP requests
- **Service Layer** – Business logic and validation
- **Repository Layer** – Data access via Spring Data JPA

### Technologies Used

- Java
- Spring Boot
- Spring MVC
- Spring Data JPA (Hibernate)
- Thymeleaf
- MySQL
- Bootstrap 5

---

## Domain Model

The system is built around a transportation network and includes the following core entities:

- **BusStation** – physical bus stations
- **Route** – connections between origin and destination stations
- **BusTrip** – scheduled trips on a specific route
- **Ticket** – reservations for passengers on a trip
- **Passenger** – customers purchasing tickets
- **Staff** – base entity for employees
- **Driver / TripManager** – specialized staff roles
- **DutyAssignment** – staff assignments to trips with defined roles

Entity relationships are implemented using JPA annotations such as:
- `@OneToMany`
- `@ManyToOne`
- `@ManyToMany`

Unique constraints are applied where necessary to ensure data integrity.

---

## Listing, Sorting & Filtering

Each main entity provides a list view supporting:

- dynamic sorting (ascending / descending)
- preserved query parameters
- optional filtering fields
- graceful handling of missing or empty parameters

Sorting is implemented using `Spring Data JPA Sort`, while filtering is handled via JPQL queries with optional parameters.

This approach ensures clean URLs and avoids unnecessary client-side logic.

---

## Business Logic & Validation

All business rules are centralized in the **service layer**, ensuring a clear separation of concerns.

Key features include:
- field validation using Bean Validation (`@Valid`)
- database-level constraints for duplicates
- custom domain-specific exceptions
- defensive handling of invalid or missing request parameters

This design guarantees predictable behavior and consistent data integrity.

---

## Error Handling & Stability

The application includes:
- custom exception handling for domain errors
- safe defaults for sorting and filtering
- user-friendly error messages displayed in the UI

As a result, the system remains stable and resistant to runtime failures caused by invalid input.

---

## Project Purpose

This project was developed as:
- a learning project for Spring Boot and JPA
- a university assignment
- a portfolio application demonstrating backend best practices

The focus is on **clarity, correctness, and maintainability**, rather than frontend-heavy complexity.

---

## Future Improvements

Possible future enhancements include:
- pagination support
- advanced filtering using Specifications
- REST API endpoints
- authentication and authorization
- improved UI and user experience

---
