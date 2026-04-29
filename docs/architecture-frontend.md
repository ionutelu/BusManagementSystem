# Architecture — Frontend (React SPA)

> Generated: 2026-04-29 | Part: `frontend` | Type: Web SPA

---

## Executive Summary

The frontend is a **React 19 Single-Page Application** built with Vite and TypeScript. It communicates exclusively with the Spring Boot backend via Axios. Server state is managed through TanStack React Query. Navigation is handled by React Router DOM v7. Styling uses TailwindCSS v4.

---

## Technology Stack

| Category | Technology | Version |
|---|---|---|
| Framework | React | 19.2.5 |
| Language | TypeScript | ~6.0.2 |
| Build | Vite | 8.0.10 |
| Styling | TailwindCSS | 4.2.4 |
| HTTP Client | Axios | 1.15.2 |
| Server State | TanStack React Query | 5.100.6 |
| Routing | React Router DOM | 7.14.2 |

---

## Architecture Pattern

**Component-Per-Page SPA** — each route maps to a full-page component. No shared component library yet; all UI is implemented directly in page files.

```
Browser
  ↓
main.tsx          — ReactDOM.createRoot, providers setup
  ↓
App.tsx           — React Router routes definition
  ↓
pages/{entity}/   — Page component (fetches, displays, and mutates entity data)
  ↓
api/{entity}.ts   — Axios call wrappers
  ↓
api/client.ts     — Central Axios instance (base URL, headers)
  ↓
Backend REST API
```

---

## Entry Points

| File | Role |
|---|---|
| `index.html` | HTML shell, mounts `#root` |
| `src/main.tsx` | Creates React root, wraps with Query/Router providers |
| `src/App.tsx` | Defines all client-side routes |

---

## Page Inventory

| Page | Route (inferred) | Entity |
|---|---|---|
| `BusesPage.tsx` | `/buses` | Bus |
| `BusStationsPage.tsx` | `/bus-stations` | BusStation |
| `BusTripsPage.tsx` | `/bus-trips` | BusTrip |
| `DriversPage.tsx` | `/drivers` | Driver |
| `PassengersPage.tsx` | `/passengers` | Passenger |
| `RoutesPage.tsx` | `/routes` | Route |
| `TicketsPage.tsx` | `/tickets` | Ticket |
| `AssignmentsPage.tsx` | `/assignments` | DutyAssignment |
| `TripManagersPage.tsx` | `/trip-managers` | TripManager |

---

## API Client Layer

`api/client.ts` — central Axios instance. Individual modules:

| File | Calls Backend |
|---|---|
| `buses.ts` | `/api/buses` |
| `busStations.ts` | `/api/bus-stations` |
| `busTrips.ts` | `/api/bus-trips` |
| `drivers.ts` | `/api/drivers` |
| `passengers.ts` | `/api/passengers` |
| `routes.ts` | `/api/routes` |
| `tickets.ts` | `/api/tickets` |
| `assignments.ts` | `/api/duty-assignments` |
| `tripManagers.ts` | `/api/trip-managers` |

---

## State Management

- **Server state**: TanStack React Query (`useQuery`, `useMutation`) — handles caching, refetching, loading/error states
- **UI state**: Local `useState` inside page components
- **No global client-side state store** (no Redux, Zustand, Context API)

---

## Notable Gaps

| Gap | Impact |
|---|---|
| `components/` folder is **empty** | All UI built inline in pages — zero reusability |
| `hooks/` folder is **empty** | No custom hooks — logic coupled into page components |
| No testing setup | No Vitest, no Testing Library |
| No component library | Manual TailwindCSS in every page (potential inconsistency) |
| No error boundary | Unhandled render errors crash the full page |
| No loading skeletons/placeholders | UX degraded on slow connections |

