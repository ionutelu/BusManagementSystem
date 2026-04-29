# Bus Station Application — Project Overview

> Generated: 2026-04-29 | Scan Level: Quick | Mode: Initial Scan

---

## Executive Summary

**Bus Station Application** is a full-stack web application for managing a bus station's operations. It provides a REST API backend built with Spring Boot and a single-page frontend built with React. The system tracks buses, routes, trips, drivers, passengers, tickets, duty assignments, and trip managers — covering the full operational lifecycle of a bus station.

---

## Project Classification

| Property | Value |
|---|---|
| Repository Type | Monorepo (2 parts in one root) |
| Backend | Spring Boot 3.5.7, Java 17, MySQL |
| Frontend | React 19, TypeScript, Vite, TailwindCSS |
| API Style | RESTful JSON |
| Database | MySQL (port 3307) |
| Architecture | Layered (Controller → Service → Repository → Database) |

---

## Tech Stack Summary

### Backend (`/src`)
| Category | Technology | Version |
|---|---|---|
| Framework | Spring Boot | 3.5.7 |
| Language | Java | 17 |
| ORM | Spring Data JPA / Hibernate | Boot-managed |
| Database | MySQL | 8.x |
| Validation | Spring Boot Starter Validation | Boot-managed |
| API Docs | SpringDoc OpenAPI (Swagger UI) | 2.5.0 |
| Test DB | H2 (in-memory) | Boot-managed |
| Testing | JUnit 5 + Mockito (Spring Boot Test) | Boot-managed |
| Build | Maven | 3.x |

### Frontend (`/frontend`)
| Category | Technology | Version |
|---|---|---|
| Framework | React | 19.2.5 |
| Language | TypeScript | ~6.0.2 |
| Build Tool | Vite | 8.0.10 |
| Styling | TailwindCSS | 4.2.4 |
| HTTP Client | Axios | 1.15.2 |
| Server State | TanStack React Query | 5.100.6 |
| Routing | React Router DOM | 7.14.2 |

---

## Domain Model at a Glance

The application manages **11 domain entities** across 3 logical groups:

**Fleet & Operations**
- `Bus` — Fleet vehicles with status tracking (`BusStatus`)
- `Route` — Origin-to-destination routes
- `BusTrip` — Individual trip instances on routes (`BusTripStatus`)
- `BusStation` — Physical station locations

**Staff**
- `Staff` — Base staff entity
- `Driver` — Bus drivers with role/assignment tracking (`DriverRole`)
- `TripManager` — Managers overseeing trips

**Customer**
- `Passenger` — Passengers with registration
- `Ticket` — Booking records linking passengers to trips
- `DutyAssignment` — Driver-to-trip duty records

---

## Repository Structure

```
Bus_Station_Aplication/
├── src/                        # Spring Boot backend (Maven)
│   └── main/java/com/example/busstation/
│       ├── api/controller/     # REST API controllers (9)
│       ├── api/dto/            # Request/Response DTOs
│       ├── api/exception/      # Global exception handler
│       ├── config/             # CORS + SPA routing config
│       ├── controller/         # Legacy MVC controllers (3)
│       ├── exception/          # Custom exception classes (16)
│       ├── model/              # JPA entities + enums
│       ├── repository/         # Spring Data JPA repos (9)
│       └── service/            # Business logic layer (9)
├── frontend/                   # React SPA (Vite)
│   └── src/
│       ├── api/                # Axios API client functions (11)
│       ├── pages/              # Route-based page components (9)
│       └── types/              # TypeScript API types
├── docs/                       # Generated project documentation (this folder)
└── _bmad-output/               # Planning artifacts
```

---

## Links

- [Architecture — Backend](./architecture-backend.md)
- [Architecture — Frontend](./architecture-frontend.md)
- [API Contracts](./api-contracts-backend.md)
- [Data Models](./data-models-backend.md)
- [Integration Architecture](./integration-architecture.md)
- [Development Guide](./development-guide.md)
- [Source Tree Analysis](./source-tree-analysis.md)
- [⚡ Improvement Opportunities](./improvement-opportunities.md)

