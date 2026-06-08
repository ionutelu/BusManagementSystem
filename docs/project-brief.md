# Project Brief: Bus Management System

## Project Overview

The **Bus Management System** is a web-based application designed to manage the full lifecycle of bus transportation operations. It serves as an administrative back-office platform that enables operators to manage their bus fleet, plan routes, schedule trips, assign staff, and handle passenger ticketing from a single interface.

## Problem Statement

Bus transportation companies need a centralized system to coordinate their day-to-day operations. Without such a system, managing the bus fleet, planning routes between stations, scheduling trips, assigning drivers to each trip, and selling and tracking tickets would require separate, disconnected processes — leading to errors, inefficiencies, and poor visibility into operational status.

## Goals and Objectives

1. **Fleet Management** — Maintain a live registry of buses including their status (active or down for maintenance) and physical capacity.
2. **Network Management** — Define and manage bus stations and the routes that connect them.
3. **Trip Scheduling** — Schedule specific bus trips on defined routes, tracking their lifecycle from planned through to completion or cancellation.
4. **Staff Management** — Manage drivers and trip managers, and assign them to trips with defined roles.
5. **Passenger & Ticketing** — Register passengers and issue tickets for specific seats on specific trips, enforcing no-duplicate-seat constraints.
6. **Operational Visibility** — Provide filtered and sorted views of all entities so that operators can quickly find what they need.

## Target Users

| Role | Description |
|---|---|
| **Operations Manager** | Plans routes, schedules trips, monitors fleet and trip status. |
| **Dispatcher** | Assigns buses and drivers to trips, manages duty assignments. |
| **Ticketing Agent** | Creates passengers and sells tickets for bus trips. |
| **Administrator** | Full system access; manages stations, routes, staff, and configuration. |

## Current System (As-Is)

The application is a **Spring Boot 3.5.7 / Java 17** web application using:

- **Spring Data JPA + MySQL** for persistence
- **Thymeleaf** for the server-side rendered web UI
- **Spring Boot Validation** for input constraints

The codebase evolved from an earlier in-memory/file-based architecture (retained in legacy classes `InMemoryRepository`, `InFileRepository`) to a full relational database approach.

## Domain Summary

| Entity | Key Attributes |
|---|---|
| `Bus` | VIN, registration number, capacity (20–80 seats), status (ACTIVE / DOWN) |
| `BusStation` | Name, city, damaged flag |
| `Route` | Origin station, destination station, distance |
| `BusTrip` | Route, bus, start time, status (PLANNED / COMPLETED / CANCELLED), stops |
| `Driver` | Extends Staff; years of experience, duty assignments |
| `TripManager` | Extends Staff; manages trips |
| `Staff` | Abstract base: name, validated email |
| `Passenger` | Name, preferred currency |
| `Ticket` | Seat number (unique per trip), price, links passenger ↔ trip |
| `DutyAssignment` | Links staff member to a trip with a role (PRIMARY / RELIEF driver) |

## Technical Stack

| Layer | Technology |
|---|---|
| Language | Java 17 |
| Framework | Spring Boot 3.5.7 |
| Persistence | Spring Data JPA, Hibernate, MySQL |
| Web / UI | Spring MVC, Thymeleaf |
| Validation | Jakarta Bean Validation (Hibernate Validator) |
| Build | Apache Maven |
| Testing | JUnit 5, Spring Boot Test |

## Scope of Refactoring (BMAD Initiative)

The current `refactoring-bmad` branch represents a transition to a cleaner, production-ready architecture. Key areas identified for improvement include:

1. **Remove dead code** — Commented-out legacy controller methods and unused in-file/in-memory repository classes should be cleaned up.
2. **Secure configuration** — Hardcoded database credentials in `application.properties` should be externalized (environment variables / secrets).
3. **Improve error handling** — Some exception catches use raw string matching (`e.getMessage().contains(...)`) and should be replaced with typed exception handling.
4. **Increase test coverage** — Only a single context-load test exists; service-layer and controller unit/integration tests are needed.
5. **Consistent validation** — Several fields have commented-out `@NotNull` annotations that need to be reviewed and enabled.
6. **API documentation** — No OpenAPI/Swagger documentation exists.

## Success Criteria

- All CRUD operations for each entity work correctly with the MySQL backend.
- Business rules (e.g. bus capacity range, unique seat per trip, unique VIN/registration) are enforced at the service and database levels.
- The web UI allows filtering and sorting for buses, trips, passengers, and tickets.
- Staff can be assigned to trips with appropriate roles.
- The codebase passes all unit and integration tests.
- No hardcoded secrets are present in committed configuration files.
