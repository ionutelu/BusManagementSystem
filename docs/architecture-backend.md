# Architecture — Backend (Spring Boot)

> Generated: 2026-04-29 | Part: `backend` | Type: REST API

---

## Executive Summary

The backend is a **Spring Boot 3.5.7** monolithic REST API that manages all bus station operations. It follows a classic **layered architecture**: Controller → Service → Repository → Database. The API is JSON-over-HTTP, documented via Swagger UI (SpringDoc OpenAPI), and persists data to a MySQL database using Spring Data JPA.

---

## Technology Stack

| Layer | Technology | Version |
|---|---|---|
| Runtime | Java | 17 |
| Framework | Spring Boot | 3.5.7 |
| Web | Spring MVC (embedded Tomcat) | Boot-managed |
| Persistence | Spring Data JPA / Hibernate | Boot-managed |
| Database | MySQL | 8.x (port 3307) |
| Validation | Spring Boot Starter Validation (JSR-380) | Boot-managed |
| API Docs | SpringDoc OpenAPI + Swagger UI | 2.5.0 |
| Tests | JUnit 5 + Mockito + H2 in-memory | Boot-managed |
| Build | Maven + Spring Boot Maven Plugin | 3.x |

---

## Architecture Pattern

**Layered Monolith** — Four distinct layers with unidirectional dependency flow:

```
HTTP Request
    ↓
[api/controller]   — Receives HTTP, maps JSON ↔ DTO, calls service
    ↓
[service]          — Business logic, validation, exception throwing
    ↓
[repository]       — Spring Data JPA interfaces (query methods, JPQL)
    ↓
[model]            — JPA entities mapped to MySQL tables
    ↓
MySQL Database
```

---

## Application Entry Point

`BusstationApplication.java` — standard `@SpringBootApplication`, auto-scans `com.example.busstation`.

---

## Package Structure

| Package | Role |
|---|---|
| `api.controller` | 9 `@RestController` classes — one per domain entity |
| `api.dto` | Request/Response DTO pairs per entity (input validation annotations) |
| `api.exception` | `ApiExceptionHandler` — `@ControllerAdvice` global error mapping |
| `config` | `CorsConfig` (CORS policy), `SpaController` (SPA route fallback) |
| `controller` | ⚠️ 3 legacy MVC controllers — Route, Ticket, TripManager |
| `exception` | 16 custom `RuntimeException` subclasses for domain errors |
| `model` | 11 JPA entities + 3 enums |
| `repository` | 9 `JpaRepository` extensions |
| `service` | 9 `@Service` classes |

---

## Domain Entities & Enums

| Entity | Enum(s) |
|---|---|
| `Bus` | `BusStatus` |
| `BusStation` | — |
| `BusTrip` | `BusTripStatus` |
| `Driver` | `DriverRole` |
| `DutyAssignment` | — |
| `Passenger` | — |
| `Route` | — |
| `Staff` | — |
| `Ticket` | — |
| `TripManager` | — |

---

## Configuration

**`application.properties`**
```properties
spring.datasource.url=jdbc:mysql://localhost:3307/busapp
spring.datasource.username=root
spring.datasource.password=parola123           # ⚠️ Hardcoded
spring.jpa.hibernate.ddl-auto=update           # ⚠️ Risky for production
spring.jpa.show-sql=true                       # ⚠️ Should be off in production
```

---

## Exception Handling

- 16 typed domain exceptions extend `RuntimeException`
- `ApiExceptionHandler` (`@ControllerAdvice`) catches and maps them to HTTP responses with `ApiErrorResponse` body
- ⚠️ `RouteNotFoundForTripException` is thrown when **any** `RuntimeException` occurs during trip save (over-broad catch)

---

## Testing

| File | Coverage |
|---|---|
| `BusstationApplicationTests` | Spring context loads |
| `BusServiceTest` | Bus service unit tests (Mockito) |
| `BusTripServiceTest` | BusTrip service unit tests (Mockito) |
| `PassengerServiceTest` | Passenger service unit tests (Mockito) |
| `RouteServiceTest` | Route service unit tests (Mockito) |

**⚠️ Not tested:** BusStation, Driver, DutyAssignment, Ticket, TripManager services; zero controller tests; zero integration/E2E tests.

---

## API Documentation

Swagger UI available at: `http://localhost:8080/swagger-ui.html`
OpenAPI JSON at: `http://localhost:8080/v3/api-docs`

