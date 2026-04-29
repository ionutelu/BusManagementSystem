# Data Models — Backend

> Generated: 2026-04-29 | Scan Level: Quick (inferred from entity classes + repository names)

---

## Entity Overview

| Entity | Repository | Enum Fields |
|---|---|---|
| `Bus` | `BusRepository` | `status: BusStatus` |
| `BusStation` | `BusStationRepository` | — |
| `BusTrip` | `TripRepository` ⚠️ | `status: BusTripStatus` |
| `Driver` | `DriverRepository` | `role: DriverRole` |
| `DutyAssignment` | `DutyAssignmentRepository` | — |
| `Passenger` | `PassengerRepository` | — |
| `Route` | `RouteRepository` | — |
| `Staff` | *(no dedicated repo)* | — |
| `Ticket` | `TicketRepository` | — |
| `TripManager` | `TripManagerRepository` | — |

> ⚠️ `BusTrip` entity uses `TripRepository` — naming inconsistency.

---

## Enumerations

### `BusStatus`
Represents the operational state of a bus (e.g., `ACTIVE`, `INACTIVE`, `MAINTENANCE` — exact values require deep scan).

### `BusTripStatus`
Trip lifecycle states — referenced in `BusTripService` filter queries:
- `PLANNED`
- `ACTIVE`
- `COMPLETED`

### `DriverRole`
Driver role classification (e.g., `MAIN`, `SUBSTITUTE` — exact values require deep scan).

---

## Inferred Relationships

Based on domain context and exception classes:

```
Route ──────────────────────────────┐
                                    │ has many
BusTrip ←── RouteNotFoundForTrip    ▼
  │         (Route is required)   BusTrip
  │
  ├──▶ Bus          (BusNotFoundForTripException)
  ├──▶ Route        (RouteNotFoundForTripException)
  │
DutyAssignment ──▶ Driver
               ──▶ BusTrip

Ticket ──▶ Passenger
       ──▶ BusTrip          (seat duplication → DuplicateSeatException)

Driver extends Staff (likely — Staff is base class)
TripManager extends Staff (likely — Staff is base class)
```

---

## Custom Validation Exceptions

The exception layer reveals business rules enforced at service level:

| Exception | Rule |
|---|---|
| `BusCapacityInvalid` | Bus capacity must be positive |
| `DuplicateVinException` | VIN must be unique per bus |
| `DuplicateRegistrationException` | Passenger registration must be unique |
| `DuplicateRouteException` | Route origin+destination pair must be unique |
| `DuplicateSeatException` | Seat number unique per trip |
| `DuplicateBusStationException` | Bus station name must be unique |
| `EmptyFieldException` | Generic required field check |
| `EmptyFieldBusStationException` | Bus station specific required field |
| `InvalidBusStatusException` | Status transition validation |
| `InvalidStationException` | Station validation |

---

## Database Configuration

```
Host:     localhost
Port:     3307   (non-standard MySQL port)
Database: busapp
User:     root
DDL:      auto=update (schema auto-evolves — no migrations)
```

> ⚠️ **No migration tool** (Flyway/Liquibase) — schema is managed by `ddl-auto=update`. This is fragile in production and cannot safely roll back schema changes.

