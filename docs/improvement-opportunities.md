# ⚡ Improvement Opportunities

> Generated: 2026-04-29 | Analyzed by: Mary (Business Analyst) — Bus Station Application

---

## Summary

The application is a solid foundation with a clean layered architecture and good domain coverage. The improvements below are organized by **priority** and **impact**, ranging from critical security fixes to UX enhancements.

---

## 🔴 Critical — Fix Before Any Production Deployment

### 1. Hardcoded Database Credentials
**File:** `src/main/resources/application.properties`

```properties
spring.datasource.password=parola123   # ← Never commit secrets
spring.datasource.username=root
```

**Problem:** Credentials are committed to version control. Anyone with repo access can access the production database.

**Fix:** Externalise via environment variables:
```properties
spring.datasource.url=${DB_URL:jdbc:mysql://localhost:3307/busapp}
spring.datasource.username=${DB_USERNAME:root}
spring.datasource.password=${DB_PASSWORD:}
```

---

### 2. `ddl-auto=update` in Production
**File:** `application.properties`

**Problem:** Hibernate auto-migrates the schema on startup. This can silently drop columns, corrupt data, or cause irreversible changes.

**Fix:** Replace with **Flyway** (or Liquibase) for versioned migrations:
```xml
<dependency>
  <groupId>org.flywaydb</groupId>
  <artifactId>flyway-core</artifactId>
</dependency>
```
Set `spring.jpa.hibernate.ddl-auto=validate` and manage schema changes via `db/migration/V1__init.sql` scripts.

---

### 3. No Authentication or Authorization
**File:** `pom.xml` — Spring Security absent

**Problem:** All 9 API resource groups are publicly accessible with no auth layer. Anyone can create, modify, or delete buses, trips, passengers, and tickets.

**Fix:** Add Spring Security with JWT or session-based auth:
```xml
<dependency>
  <groupId>org.springframework.boot</groupId>
  <artifactId>spring-boot-starter-security</artifactId>
</dependency>
```
Define roles: e.g., `ADMIN`, `DRIVER`, `PASSENGER` — restrict mutation endpoints accordingly.

---

## 🟠 High — Address Soon

### 4. Legacy Controller Package Confusion
**Files:** `controller/RouteController.java`, `controller/TicketController.java`, `controller/TripManagerController.java`

**Problem:** There are **two controller namespaces** — `api/controller/` (the main REST API) and `controller/` (3 older controllers). This creates confusion about which controllers are active, may cause route conflicts, and makes onboarding harder.

**Fix:** Audit whether the 3 legacy controllers are still needed. If not, delete them. If they serve a different purpose, document it clearly or merge into the API controllers.

---

### 5. `show-sql=true` in All Environments
**Problem:** SQL statements are logged to stdout in development AND likely production. This leaks schema details and floods logs.

**Fix:**
```properties
# application.properties (base)
spring.jpa.show-sql=false

# application-dev.properties (dev only)
spring.jpa.show-sql=true
logging.level.org.hibernate.SQL=DEBUG
```

---

### 6. Repository Naming Inconsistency
**File:** `repository/TripRepository.java` handles `BusTrip` entity

**Problem:** Every other repository is named `{EntityName}Repository` (e.g., `BusRepository`, `PassengerRepository`) — except `TripRepository` for `BusTrip`. This breaks the naming convention and causes confusion.

**Fix:** Rename to `BusTripRepository` for consistency.

---

### 7. Over-Broad Exception Catch in BusTripService
**File:** `service/BusTripService.java`

**Problem:** The `save()` method catches **any** `RuntimeException` and wraps it as `RouteNotFoundForTripException`. This masks real errors (NullPointerException, constraint violations, etc.) as a "route not found" error — misleading for debugging.

**Fix:** Catch only specific JPA/DB exceptions (e.g., `DataIntegrityViolationException`) or validate the route FK before calling `save()`.

---

### 8. No Pagination on List Endpoints
**Problem:** All GET-all endpoints return full entity lists. For `BusTrip` or `Ticket` with thousands of records, this will cause memory issues and slow API responses.

**Fix:** Introduce `Pageable` support:
```java
@GetMapping
public Page<BusTripResponseDto> findAll(
  @RequestParam(defaultValue = "0") int page,
  @RequestParam(defaultValue = "20") int size) {
    return service.findAll(PageRequest.of(page, size));
}
```

---

## 🟡 Medium — Improve Code Quality & Maintainability

### 9. Incomplete Test Coverage (Backend)
**Currently tested:** 4 of 9 services (BusService, BusTripService, PassengerService, RouteService)

**Missing tests:** BusStationService, DriverService, DutyAssignmentService, TicketService, TripManagerService — and **zero controller tests**.

**Fix:** Add unit tests for remaining services + integration tests for API controllers using `@WebMvcTest` + MockMvc.

---

### 10. Zero Frontend Tests
**Problem:** The frontend has no test infrastructure at all (no Vitest, no Testing Library).

**Fix:** Add Vitest + React Testing Library:
```bash
npm install -D vitest @testing-library/react @testing-library/user-event jsdom
```
Start with smoke tests for each page component and API client functions.

---

### 11. Empty `components/` and `hooks/` Folders
**Problem:** All UI logic lives inside page components. There are no shared components or custom hooks, meaning the same patterns (forms, tables, modals, loading states) are duplicated across 9 pages.

**Fix:**
- Extract reusable `<DataTable>`, `<EntityForm>`, `<LoadingSpinner>`, `<ErrorAlert>` components into `components/`
- Extract `useEntities()` data-fetching patterns into `hooks/`

---

### 12. No Global Error Boundary (Frontend)
**Problem:** If any page component throws a render error, the whole React tree unmounts and the user sees a blank page.

**Fix:** Wrap with a React Error Boundary:
```tsx
// src/components/ErrorBoundary.tsx
class ErrorBoundary extends React.Component { ... }
```

---

### 13. No Spring Profiles (dev/prod)
**Problem:** `application.properties` is the only config file. There's no separation between dev and production settings.

**Fix:** Create:
- `application.properties` — shared base config
- `application-dev.properties` — dev overrides (show-sql=true, etc.)
- `application-prod.properties` — production overrides (ddl-auto=none, show-sql=false)

Activate with: `SPRING_PROFILES_ACTIVE=prod`

---

## 🟢 Low — Nice-to-Have Enhancements

### 14. No Docker / docker-compose
**Problem:** No containerisation setup exists, making local setup dependent on a specific MySQL port and manual DB creation.

**Fix:** Add `docker-compose.yml` for local development:
```yaml
services:
  db:
    image: mysql:8
    ports: ["3307:3306"]
    environment:
      MYSQL_DATABASE: busapp
      MYSQL_ROOT_PASSWORD: ${DB_PASSWORD}
  backend:
    build: .
    ports: ["8080:8080"]
    depends_on: [db]
```

---

### 15. Non-Standard MySQL Port (3307)
**Problem:** MySQL is on port 3307 instead of the standard 3306. This is likely due to a local conflict, but it's not documented anywhere and will silently break for any new developer.

**Fix:** Document the reason in `development-guide.md`, or normalise to 3306 using Docker.

---

### 16. No CI/CD Pipeline
**Problem:** No `.github/workflows/`, `.gitlab-ci.yml`, or similar. No automated test runs on push.

**Fix:** Add a GitHub Actions workflow:
```yaml
# .github/workflows/ci.yml
- Run: ./mvnw test
- Run: cd frontend && npm run lint && npm run build
```

---

### 17. No Component/UI Library (Frontend)
**Problem:** All UI is hand-crafted TailwindCSS. Consistency across 9 pages depends entirely on discipline, and there is no design system.

**Fix:** Consider adopting a headless UI library like **shadcn/ui** (works natively with TailwindCSS) for consistent table, form, dialog, and button components.

---

## Improvement Priority Matrix

| # | Improvement | Priority | Effort | Impact |
|---|---|---|---|---|
| 1 | Externalise DB credentials | 🔴 Critical | Low | High |
| 2 | Add Flyway migrations | 🔴 Critical | Medium | High |
| 3 | Add authentication (Spring Security) | 🔴 Critical | High | High |
| 4 | Remove/audit legacy controllers | 🟠 High | Low | Medium |
| 5 | Disable show-sql in production | 🟠 High | Low | Medium |
| 6 | Rename TripRepository → BusTripRepository | 🟠 High | Low | Low |
| 7 | Fix over-broad exception catch | 🟠 High | Low | Medium |
| 8 | Add pagination to list endpoints | 🟠 High | Medium | High |
| 9 | Complete backend test coverage | 🟡 Medium | Medium | High |
| 10 | Add frontend test infrastructure | 🟡 Medium | Medium | High |
| 11 | Extract shared components & hooks | 🟡 Medium | High | High |
| 12 | Add React Error Boundary | 🟡 Medium | Low | Medium |
| 13 | Add Spring profiles (dev/prod) | 🟡 Medium | Low | Medium |
| 14 | Add Docker / docker-compose | 🟢 Low | Medium | Medium |
| 15 | Document/normalise MySQL port | 🟢 Low | Low | Low |
| 16 | Add CI/CD pipeline | 🟢 Low | Medium | High |
| 17 | Adopt shadcn/ui component library | 🟢 Low | High | Medium |

---

*For any of these, I can help you plan the implementation, create stories, or hand off to the Developer agent (`bmad-agent-dev`). Just say the word!*

