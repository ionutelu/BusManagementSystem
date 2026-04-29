# Source Tree Analysis

> Generated: 2026-04-29 | Scan Level: Quick

---

## Repository Root

```
Bus_Station_Aplication/                       ← Monorepo root
│
├── pom.xml                                   ← Maven build descriptor (Spring Boot backend)
├── mvnw / mvnw.cmd                           ← Maven wrapper scripts
│
├── src/                                      ← Spring Boot backend source
│   ├── main/
│   │   ├── java/com/example/busstation/
│   │   │   ├── BusstationApplication.java    ← Spring Boot entry point (@SpringBootApplication)
│   │   │   │
│   │   │   ├── api/                          ← Public REST API layer
│   │   │   │   ├── controller/               ← 9 REST controllers (one per domain entity)
│   │   │   │   │   ├── BusApiController.java
│   │   │   │   │   ├── BusStationApiController.java
│   │   │   │   │   ├── BusTripApiController.java
│   │   │   │   │   ├── DriverApiController.java
│   │   │   │   │   ├── DutyAssignmentApiController.java
│   │   │   │   │   ├── PassengerApiController.java
│   │   │   │   │   ├── RouteApiController.java
│   │   │   │   │   ├── TicketApiController.java
│   │   │   │   │   └── TripManagerApiController.java
│   │   │   │   ├── dto/                      ← Request/Response DTOs per entity
│   │   │   │   │   ├── bus/                  ← BusRequestDto, BusResponseDto
│   │   │   │   │   ├── busstation/
│   │   │   │   │   ├── bustrip/
│   │   │   │   │   ├── driver/
│   │   │   │   │   ├── dutyassignment/
│   │   │   │   │   ├── passenger/
│   │   │   │   │   ├── route/
│   │   │   │   │   ├── ticket/
│   │   │   │   │   ├── tripmanager/
│   │   │   │   │   └── ApiErrorResponse.java ← Standard error response shape
│   │   │   │   └── exception/
│   │   │   │       └── ApiExceptionHandler.java ← @ControllerAdvice global handler
│   │   │   │
│   │   │   ├── config/
│   │   │   │   ├── CorsConfig.java           ← Cross-origin config (frontend ↔ backend)
│   │   │   │   └── SpaController.java        ← Fallback route for React SPA
│   │   │   │
│   │   │   ├── controller/                   ← ⚠️ Legacy MVC controllers (3 entities only)
│   │   │   │   ├── RouteController.java
│   │   │   │   ├── TicketController.java
│   │   │   │   └── TripManagerController.java
│   │   │   │
│   │   │   ├── exception/                    ← 16 custom domain exception classes
│   │   │   │   ├── BusCapacityInvalid.java
│   │   │   │   ├── BusNotFoundException.java
│   │   │   │   ├── BusNotFoundForTripException.java
│   │   │   │   ├── BusStationNotFoundException.java
│   │   │   │   ├── BusTripNotFoundException.java
│   │   │   │   ├── DuplicateBusStationException.java
│   │   │   │   ├── DuplicateRegistrationException.java
│   │   │   │   ├── DuplicateRouteException.java
│   │   │   │   ├── DuplicateSeatException.java
│   │   │   │   ├── DuplicateVinException.java
│   │   │   │   ├── EmptyFieldBusStationException.java
│   │   │   │   ├── EmptyFieldException.java
│   │   │   │   ├── InvalidBusStatusException.java
│   │   │   │   ├── InvalidStationException.java
│   │   │   │   ├── PassengerNotFoundException.java
│   │   │   │   ├── RouteNotFoundException.java
│   │   │   │   └── RouteNotFoundForTripException.java
│   │   │   │
│   │   │   ├── model/                        ← JPA entity classes + enums
│   │   │   │   ├── Bus.java, BusStatus.java
│   │   │   │   ├── BusStation.java
│   │   │   │   ├── BusTrip.java, BusTripStatus.java
│   │   │   │   ├── Driver.java, DriverRole.java
│   │   │   │   ├── DutyAssignment.java
│   │   │   │   ├── Passenger.java
│   │   │   │   ├── Route.java
│   │   │   │   ├── Staff.java                ← ⚠️ Base class or standalone? (check inheritance)
│   │   │   │   ├── Ticket.java
│   │   │   │   └── TripManager.java
│   │   │   │
│   │   │   ├── repository/                   ← Spring Data JPA repositories
│   │   │   │   ├── BusRepository.java
│   │   │   │   ├── BusStationRepository.java
│   │   │   │   ├── DriverRepository.java
│   │   │   │   ├── DutyAssignmentRepository.java
│   │   │   │   ├── PassengerRepository.java
│   │   │   │   ├── RouteRepository.java
│   │   │   │   ├── TicketRepository.java
│   │   │   │   ├── TripManagerRepository.java
│   │   │   │   └── TripRepository.java       ← ⚠️ Naming mismatch: BusTrip entity vs TripRepository
│   │   │   │
│   │   │   └── service/                      ← Business logic layer
│   │   │       ├── BusService.java
│   │   │       ├── BusStationService.java
│   │   │       ├── BusTripService.java
│   │   │       ├── DriverService.java
│   │   │       ├── DutyAssignmentService.java
│   │   │       ├── PassengerService.java
│   │   │       ├── RouteService.java
│   │   │       ├── TicketService.java
│   │   │       └── TripManagerService.java
│   │   │
│   │   └── resources/
│   │       └── application.properties        ← ⚠️ Hardcoded DB credentials + show-sql=true
│   │
│   └── test/
│       └── java/com/example/busstation/
│           ├── BusstationApplicationTests.java ← Spring Boot context load test
│           └── service/                        ← Unit tests (4 of 9 services covered)
│               ├── BusServiceTest.java
│               ├── BusTripServiceTest.java
│               ├── PassengerServiceTest.java
│               └── RouteServiceTest.java
│
├── frontend/                                 ← React SPA (separate Vite project)
│   ├── package.json                          ← npm manifest
│   ├── vite.config.ts                        ← Vite config
│   ├── tsconfig*.json                        ← TypeScript configs
│   ├── index.html                            ← SPA entry HTML
│   └── src/
│       ├── main.tsx                          ← React entry point (ReactDOM.createRoot)
│       ├── App.tsx                           ← Root component + router setup
│       ├── App.css / index.css               ← Global styles
│       ├── api/                              ← Axios API client modules (one per entity)
│       │   ├── client.ts                     ← Axios instance configuration
│       │   ├── buses.ts, busStations.ts
│       │   ├── busTrips.ts, routes.ts
│       │   ├── drivers.ts, passengers.ts
│       │   ├── tickets.ts, assignments.ts
│       │   └── tripManagers.ts
│       ├── components/                       ← ⚠️ EMPTY — no shared components yet
│       ├── hooks/                            ← ⚠️ EMPTY — no custom hooks yet
│       ├── pages/                            ← Full-page route components (9 pages)
│       │   ├── buses/BusesPage.tsx
│       │   ├── busStations/BusStationsPage.tsx
│       │   ├── busTrips/BusTripsPage.tsx
│       │   ├── drivers/DriversPage.tsx
│       │   ├── passengers/PassengersPage.tsx
│       │   ├── routes/RoutesPage.tsx
│       │   ├── tickets/TicketsPage.tsx
│       │   ├── assignments/AssignmentsPage.tsx
│       │   └── tripManagers/TripManagersPage.tsx
│       ├── types/
│       │   └── api.ts                        ← Shared TypeScript interfaces for API types
│       └── assets/                           ← Static assets (hero.png, SVGs)
│
├── docs/                                     ← Project documentation (this folder)
└── _bmad-output/                             ← BMad planning artifacts
    └── planning-artifacts/
        └── architecture.md
```

