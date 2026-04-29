# Product Requirements Document (PRD)
## Bus Management System

**Version:** 1.0  
**Status:** Draft  
**Author:** BMAD Analyst Agent  
**Date:** 2026-04-29  
**Base Branch:** `refactoring-bmad`

---

## 1. Introduction

### 1.1 Purpose

This document defines the functional and non-functional requirements for the Bus Management System — a web-based back-office platform that manages bus fleet operations, route networks, trip scheduling, staff assignments, and passenger ticketing.

### 1.2 Scope

The system covers all core administrative operations required to run a bus transportation company:

- Fleet (bus) management
- Station and route network management
- Trip scheduling and lifecycle management
- Driver and trip manager administration
- Passenger registration and ticket issuance
- Duty assignment of staff to trips

Out of scope for this version: passenger-facing booking portal, mobile applications, real-time GPS tracking, payment gateway integration.

### 1.3 Definitions

| Term | Definition |
|---|---|
| **Trip** | A single scheduled journey of a bus along a route |
| **Route** | A directional connection between two bus stations |
| **Duty Assignment** | The formal allocation of a staff member to a trip with a specific role |
| **Staff** | Any employee (Driver or TripManager) in the system |

---

## 2. Goals

| # | Goal | Priority |
|---|---|---|
| G-1 | Manage the full bus fleet lifecycle (register, update status, retire) | High |
| G-2 | Define and maintain the station/route network | High |
| G-3 | Schedule and manage bus trips end-to-end | High |
| G-4 | Track and assign staff (drivers, managers) to trips | High |
| G-5 | Issue and manage passenger tickets | High |
| G-6 | Provide filtered and sorted list views for operational use | Medium |
| G-7 | Enforce business rules at both the application and database levels | High |
| G-8 | Maintain a clean, maintainable, well-tested codebase | Medium |

---

## 3. User Stories

### 3.1 Fleet Management (Bus)

| ID | As a… | I want to… | So that… |
|---|---|---|---|
| US-B-01 | Operations Manager | View all buses with their current status | I can see which buses are available |
| US-B-02 | Operations Manager | Filter buses by VIN, status, or minimum capacity | I can quickly find buses that meet trip requirements |
| US-B-03 | Operations Manager | Sort the bus list by any field (asc/desc) | I can identify patterns (e.g. lowest capacity first) |
| US-B-04 | Administrator | Register a new bus with VIN, registration number, capacity, and status | New fleet additions are recorded immediately |
| US-B-05 | Administrator | Edit an existing bus's details | I can update capacity, registration, or status after changes |
| US-B-06 | Administrator | Delete a bus from the system | Decommissioned buses no longer appear in scheduling |

**Business Rules:**
- BR-B-01: Bus capacity must be between 20 and 80 seats (inclusive).
- BR-B-02: VIN must be unique across all buses.
- BR-B-03: Registration number must be unique across all buses.
- BR-B-04: Bus status must be one of `ACTIVE` or `DOWN`.

---

### 3.2 Station Management (BusStation)

| ID | As a… | I want to… | So that… |
|---|---|---|---|
| US-S-01 | Administrator | View all bus stations | I have a complete view of the network |
| US-S-02 | Administrator | Add a new bus station with name, city, and damage flag | New infrastructure is registered in the system |
| US-S-03 | Administrator | Edit a station's details (e.g. mark it as damaged) | The network reflects current physical conditions |
| US-S-04 | Administrator | Delete a bus station | Closed stations are removed from routing |

**Business Rules:**
- BR-S-01: The combination of (name, city) must be unique — no duplicate stations in the same city.
- BR-S-02: Name and city are required fields.

---

### 3.3 Route Management (Route)

| ID | As a… | I want to… | So that… |
|---|---|---|---|
| US-R-01 | Operations Manager | View all routes | I can see the full network topology |
| US-R-02 | Operations Manager | Create a route between two stations with a distance | New connections are available for trip scheduling |
| US-R-03 | Operations Manager | Edit a route's origin, destination, or distance | Routes can be corrected if stations change |
| US-R-04 | Operations Manager | Delete a route | Discontinued routes are removed |

**Business Rules:**
- BR-R-01: The combination of (origin station, destination station) must be unique — no duplicate routes.
- BR-R-02: Distance must be greater than 0.
- BR-R-03: Origin and destination stations must already exist in the system.
- BR-R-04: Origin and destination must be different stations.

---

### 3.4 Trip Scheduling (BusTrip)

| ID | As a… | I want to… | So that… |
|---|---|---|---|
| US-T-01 | Dispatcher | View all trips with route, bus, start time, and status | I have a full operational schedule |
| US-T-02 | Dispatcher | Filter trips by route or status | I can focus on planned, completed, or cancelled trips |
| US-T-03 | Dispatcher | Sort trips by any field | I can find the next scheduled trips quickly |
| US-T-04 | Dispatcher | Schedule a new trip by selecting a route, bus, and start time | A new journey is added to the schedule |
| US-T-05 | Dispatcher | Edit a trip's route, bus, start time, or status | Scheduling changes are reflected immediately |
| US-T-06 | Dispatcher | Cancel or complete a trip | Trip lifecycle transitions are tracked |
| US-T-07 | Dispatcher | Add bus stations as stops to a trip | The trip's intermediate stops are defined |
| US-T-08 | Dispatcher | View the full details of a trip (tickets, duty assignments) | I have a complete picture of who is on the trip |
| US-T-09 | Dispatcher | Delete a trip | Erroneous trips can be removed |

**Business Rules:**
- BR-T-01: A trip must reference a valid, existing route and bus.
- BR-T-02: Start time is required.
- BR-T-03: Default status when creating a trip is `PLANNED`.
- BR-T-04: Trip status must be one of `PLANNED`, `COMPLETED`, or `CANCELLED`.

---

### 3.5 Driver Management (Driver)

| ID | As a… | I want to… | So that… |
|---|---|---|---|
| US-D-01 | Administrator | View all drivers | I can see all available driving staff |
| US-D-02 | Administrator | Add a new driver with name, email, and years of experience | New hires are onboarded into the system |
| US-D-03 | Administrator | Edit a driver's details | Driver records can be updated |
| US-D-04 | Administrator | Delete a driver | Departed drivers are removed |

**Business Rules:**
- BR-D-01: Name and email are required; email must be valid.
- BR-D-02: Years of experience must be a positive number.

---

### 3.6 Trip Manager Management (TripManager)

| ID | As a… | I want to… | So that… |
|---|---|---|---|
| US-TM-01 | Administrator | View all trip managers | I can see all managerial staff |
| US-TM-02 | Administrator | Add, edit, and delete trip managers | Staff roster is kept current |

**Business Rules:**
- BR-TM-01: Name and email are required; email must be valid.

---

### 3.7 Duty Assignment (DutyAssignment)

| ID | As a… | I want to… | So that… |
|---|---|---|---|
| US-DA-01 | Dispatcher | View all duty assignments | I can see who is assigned to which trip |
| US-DA-02 | Dispatcher | Assign a staff member to a trip with a role | Every trip has a designated driver and (optionally) relief staff |
| US-DA-03 | Dispatcher | Change the role of an assignment | Roles can be adjusted when the crew changes |
| US-DA-04 | Dispatcher | Remove a duty assignment | Incorrect assignments can be corrected |

**Business Rules:**
- BR-DA-01: Both the trip and the staff member must exist.
- BR-DA-02: Role must be one of `PRIMARY_DRIVER` or `RELIEF_DRIVER`.
- BR-DA-03: Default role is `PRIMARY_DRIVER`.

---

### 3.8 Passenger Management (Passenger)

| ID | As a… | I want to… | So that… |
|---|---|---|---|
| US-P-01 | Ticketing Agent | View all passengers | I can look up existing records before issuing tickets |
| US-P-02 | Ticketing Agent | Filter and sort the passenger list | I can find a passenger quickly |
| US-P-03 | Ticketing Agent | Register a new passenger | New travellers can be ticketed |
| US-P-04 | Ticketing Agent | Edit passenger details | Records stay accurate |
| US-P-05 | Ticketing Agent | Delete a passenger | Duplicate or test records can be removed |

**Business Rules:**
- BR-P-01: Name and currency are required.

---

### 3.9 Ticket Management (Ticket)

| ID | As a… | I want to… | So that… |
|---|---|---|---|
| US-TK-01 | Ticketing Agent | View all tickets with trip, passenger, seat, and price | I have a full booking register |
| US-TK-02 | Ticketing Agent | Filter tickets by trip, passenger name, or maximum price | I can quickly find specific bookings |
| US-TK-03 | Ticketing Agent | Sort the ticket list by any field | Reports can be ordered as needed |
| US-TK-04 | Ticketing Agent | Issue a ticket for a passenger on a specific trip and seat | Passengers are formally booked onto a trip |
| US-TK-05 | Ticketing Agent | Edit a ticket's seat number or price | Corrections and upgrades can be made |
| US-TK-06 | Ticketing Agent | Delete a ticket | Cancelled bookings are removed |

**Business Rules:**
- BR-TK-01: Seat number must be unique per trip — two passengers cannot share a seat on the same trip.
- BR-TK-02: Seat number is required.
- BR-TK-03: Price is required and must be positive.
- BR-TK-04: Both the bus trip and the passenger must exist before a ticket can be issued.

---

## 4. Non-Functional Requirements

### 4.1 Performance

| ID | Requirement |
|---|---|
| NFR-P-01 | List views (buses, trips, tickets) must load within 2 seconds for up to 1,000 records with filtering and sorting applied. |
| NFR-P-02 | All CRUD write operations must complete within 500 ms under normal load. |

### 4.2 Security

| ID | Requirement |
|---|---|
| NFR-SEC-01 | Database credentials must not be hardcoded in committed source files; they must be supplied via environment variables or an externalized secret store. |
| NFR-SEC-02 | All user-supplied input must be validated before persistence. |
| NFR-SEC-03 | SQL injection must be prevented; all queries must use parameterized/JPQL statements (no string concatenation in queries). |

### 4.3 Reliability

| ID | Requirement |
|---|---|
| NFR-R-01 | All business rule violations must return a meaningful error message to the UI (not a stack trace). |
| NFR-R-02 | Duplicate-key violations at the database level must be caught and translated into user-friendly exceptions. |

### 4.4 Maintainability

| ID | Requirement |
|---|---|
| NFR-M-01 | Dead code (commented-out methods, unused classes) must be removed. |
| NFR-M-02 | Service-layer unit tests must cover at minimum: happy path, not-found exceptions, and business rule violations for each entity. |
| NFR-M-03 | Controller integration tests must cover at minimum the index (list), create, and delete endpoints for each entity. |

### 4.5 Compatibility

| ID | Requirement |
|---|---|
| NFR-C-01 | The application must run on Java 17+. |
| NFR-C-02 | The application must be buildable with `mvn clean package` without requiring external services. |

---

## 5. Technical Architecture

### 5.1 Layered Architecture

```
┌──────────────────────────────────────────┐
│         Thymeleaf Templates (UI)         │
├──────────────────────────────────────────┤
│       Spring MVC Controllers             │
│  (BusController, TripController, ...)    │
├──────────────────────────────────────────┤
│          Service Layer                   │
│  (BusService, BusTripService, ...)       │
├──────────────────────────────────────────┤
│        Repository Layer (JPA)            │
│  (BusRepository, TripRepository, ...)    │
├──────────────────────────────────────────┤
│           MySQL Database                 │
└──────────────────────────────────────────┘
```

### 5.2 Data Model Relationships

```
BusStation ←─(origin/destination)─ Route ←──── BusTrip ───────→ Bus
                                                    │
                            ┌───────────────────────┼────────────────┐
                            ↓                       ↓                ↓
                          Ticket           DutyAssignment    BusStation
                            │                       │         (stops, M:N)
                            ↓                       ↓
                        Passenger                 Staff
                                               (Driver / TripManager)
```

### 5.3 Inheritance Strategy

`Staff` uses JPA `JOINED` table inheritance:
- `staff` table holds common fields (id, name, email)
- `drivers` table holds `staff_id` FK + `experienceYears`
- `trip_managers` table holds `staff_id` FK (no extra fields in current implementation)

### 5.4 Key Constraints (Database Level)

| Table | Constraint |
|---|---|
| `buses` | UNIQUE on `vin`; UNIQUE on `registrationNumber` |
| `bus_stations` | UNIQUE on `(name, city)` |
| `routes` | UNIQUE on `(origin_station_id, destination_station_id)` |
| `tickets` | UNIQUE on `(seat_number, bus_trip_id)` |

---

## 6. Open Issues and Risks

| # | Issue / Risk | Recommendation |
|---|---|---|
| OI-01 | Hardcoded database password in `application.properties` | Externalize to environment variable (`DB_PASSWORD`) before any deployment |
| OI-02 | Commented-out `@NotNull` annotations on `Ticket.busTrip` and `Ticket.passenger` | Review and re-enable; validate at the service layer if annotation is insufficient |
| OI-03 | `BusTripController.update()` catches `RuntimeException` and always throws `RouteNotFoundForTripException`, hiding the actual cause | Fix to use typed exception handling |
| OI-04 | Legacy classes `InMemoryRepository`, `InFileRepository`, `AbstractRepository`, `Identifiable` are unused after JPA migration | Remove to reduce confusion and maintenance burden |
| OI-05 | `HelloController` exists but no meaningful homepage logic | Implement a dashboard or remove if unused |
| OI-06 | Test coverage is near-zero (only `contextLoads` test) | Add unit tests for services and MockMvc tests for controllers |
| OI-07 | No `toString()` override on `BusStation` | Add for consistent logging |
| OI-08 | `BusStation` constructor takes a `List<BusTrip>` parameter but does not assign it | Fix constructor or remove the parameter |

---

## 7. Acceptance Criteria (Definition of Done)

- [ ] All CRUD endpoints for all 9 entities function correctly against the MySQL database.
- [ ] All business rules (BR-*) are enforced and tested.
- [ ] No hardcoded credentials exist in committed files.
- [ ] Dead code and unused legacy classes are removed.
- [ ] Each service class has unit tests covering at minimum happy path and exception paths.
- [ ] `mvn clean test` passes with no failures.
- [ ] All global exception handler mappings return meaningful HTTP status codes and messages (not 500 for user errors).
