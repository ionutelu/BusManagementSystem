# API Contracts — Backend

> Generated: 2026-04-29 | Scan Level: Quick (inferred from controller class names)
> Live Swagger UI: `http://localhost:8080/swagger-ui.html`

---

## Base URL

```
http://localhost:8080/api
```

---

## Endpoint Groups

| Controller | Base Path | Entity |
|---|---|---|
| `BusApiController` | `/api/buses` | Bus |
| `BusStationApiController` | `/api/bus-stations` | BusStation |
| `BusTripApiController` | `/api/bus-trips` | BusTrip |
| `DriverApiController` | `/api/drivers` | Driver |
| `DutyAssignmentApiController` | `/api/duty-assignments` | DutyAssignment |
| `PassengerApiController` | `/api/passengers` | Passenger |
| `RouteApiController` | `/api/routes` | Route |
| `TicketApiController` | `/api/tickets` | Ticket |
| `TripManagerApiController` | `/api/trip-managers` | TripManager |

---

## Standard CRUD Pattern (per entity)

Each controller follows a consistent REST pattern:

| Method | Path | Description |
|---|---|---|
| `GET` | `/api/{entity}` | List all (+ optional filter/sort params) |
| `GET` | `/api/{entity}/{id}` | Get by ID |
| `POST` | `/api/{entity}` | Create |
| `PUT` | `/api/{entity}/{id}` | Update |
| `DELETE` | `/api/{entity}/{id}` | Delete |

> **Note:** BusTrip controller exposes additional filter/sort query params (confirmed by `BusTripService.findFilteredAndSorted`).

---

## DTO Shapes (Quick Scan — inferred from class names)

All entities have matching `*RequestDto` (input) and `*ResponseDto` (output) pairs:

| Entity | Request DTO | Response DTO |
|---|---|---|
| Bus | `BusRequestDto` | `BusResponseDto` |
| BusStation | `BusStationRequestDto` | `BusStationResponseDto` |
| BusTrip | `BusTripRequestDto` | `BusTripResponseDto` |
| Driver | `DriverRequestDto` | `DriverResponseDto` |
| DutyAssignment | `DutyAssignmentRequestDto` | `DutyAssignmentResponseDto` |
| Passenger | `PassengerRequestDto` | `PassengerResponseDto` |
| Route | `RouteRequestDto` | `RouteResponseDto` |
| Ticket | `TicketRequestDto` | `TicketResponseDto` |
| TripManager | `TripManagerRequestDto` | `TripManagerResponseDto` |

---

## Error Response Shape

```json
{
  "status": 404,
  "error": "Not Found",
  "message": "Bus with id 42 not found"
}
```
_(Structure inferred from `ApiErrorResponse.java` — exact fields require deep scan)_

---

## Authentication

⚠️ **No authentication layer detected.** All endpoints appear to be publicly accessible. Spring Security is not in `pom.xml`.

---

## Pagination

⚠️ **No pagination detected.** Endpoints likely return full entity lists. For large datasets this becomes a performance concern.

---

## Legacy Controllers

Three older MVC controllers exist in `com.example.busstation.controller` (not `api.controller`):

| Controller | Likely Path |
|---|---|
| `RouteController` | `/routes` (non-API) or overlapping `/api/routes` |
| `TicketController` | `/tickets` or overlapping `/api/tickets` |
| `TripManagerController` | `/trip-managers` or overlapping |

> ⚠️ These may conflict with or duplicate the API controllers. Requires deep scan to confirm purpose.

