# Integration Architecture

> Generated: 2026-04-29

---

## Overview

This is a **2-part monorepo**: a Spring Boot backend and a React SPA frontend. They are deployed as separate processes but can be served from the same origin via the `SpaController` fallback.

---

## Integration Map

```
┌────────────────────────────────────────┐
│            Browser (React SPA)         │
│                                        │
│  pages/ ──▶ api/*.ts ──▶ client.ts     │
│                              │         │
│                         Axios HTTP     │
└──────────────────────────────┼─────────┘
                               │
                   JSON over HTTP/REST
                    (port 8080)
                               │
┌──────────────────────────────▼─────────┐
│         Spring Boot Backend            │
│                                        │
│  api/controller/ ──▶ service/          │
│       ──▶ repository/ ──▶ MySQL        │
└────────────────────────────────────────┘
```

---

## Integration Points

| From | To | Type | Details |
|---|---|---|---|
| React SPA (`frontend/`) | Spring Boot (`src/`) | REST / JSON over HTTP | Axios client → `/api/*` endpoints |

---

## CORS Configuration

`CorsConfig.java` configures Spring MVC to allow cross-origin requests from the frontend dev server (Vite default: `localhost:5173`). In production, the frontend is served as static files by Spring Boot itself (via `SpaController` + static resources in `/target/classes/static`), so CORS is not needed.

---

## Frontend Dev Proxy

- **Dev mode**: Frontend (port 5173) makes cross-origin calls to backend (port 8080). CORS header allows this.
- **Production mode**: Frontend build artifacts are placed in `target/classes/static/` and served by Spring Boot directly. `SpaController` handles SPA routing fallback.

---

## Data Flow Example (List Buses)

```
1. User navigates to /buses in browser
2. App.tsx routes to BusesPage.tsx
3. BusesPage calls useQuery → buses.ts → client.ts (Axios)
4. GET http://localhost:8080/api/buses
5. BusApiController.findAll() → BusService.findAll()
6. BusRepository.findAll() → MySQL SELECT
7. JSON response: BusResponseDto[]
8. React Query caches result → BusesPage renders table
```

