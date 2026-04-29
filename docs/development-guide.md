# Development Guide

> Generated: 2026-04-29

---

## Prerequisites

| Tool | Version | Notes |
|---|---|---|
| Java | 17 | Required by Spring Boot 3.5.7 |
| Maven | 3.x | Or use `./mvnw` wrapper (included) |
| MySQL | 8.x | Must run on port **3307** (non-standard) |
| Node.js | 18+ | For frontend development |
| npm | 9+ | Comes with Node.js |

---

## Backend Setup

### 1. Start MySQL

Ensure MySQL is running on port `3307` with:
- Database: `busapp`
- User: `root`
- Password: `parola123`

```sql
CREATE DATABASE busapp;
```

> ⚠️ Credentials are hardcoded in `src/main/resources/application.properties`. See improvement opportunities for securing this.

### 2. Run the Backend

```bash
# Using Maven wrapper (recommended)
./mvnw spring-boot:run

# Or standard Maven
mvn spring-boot:run
```

Backend listens on: `http://localhost:8080`

Swagger UI: `http://localhost:8080/swagger-ui.html`

### 3. Run Backend Tests

```bash
./mvnw test
```

Tests use H2 in-memory database (configured in `src/test/resources/application.properties`).

---

## Frontend Setup

### 1. Install Dependencies

```bash
cd frontend
npm install
```

### 2. Run Dev Server

```bash
npm run dev
```

Frontend dev server: `http://localhost:5173`

> The frontend calls the backend at `localhost:8080` via the configured Axios client. Ensure the backend is running first.

### 3. Build for Production

```bash
npm run build
```

Build output goes to `frontend/dist/`. To serve via Spring Boot, copy the build output to `src/main/resources/static/` (or configure Vite `outDir` accordingly).

### 4. Lint

```bash
npm run lint
```

---

## Environment Variables

All sensitive configuration is read from environment variables with safe local-dev fallbacks baked in. You do **not** need to set anything to run locally — the defaults work out of the box.

| Variable | Default | Description |
|---|---|---|
| `APP_NAME` | `Busstation` | Spring application name |
| `DB_URL` | `jdbc:mysql://localhost:3307/busapp` | JDBC connection URL |
| `DB_USERNAME` | `root` | Database user |
| `DB_PASSWORD` | `parola123` | Database password |
| `DB_DRIVER` | `com.mysql.cj.jdbc.Driver` | JDBC driver class |

### Using a `.env` file (optional)

For a cleaner setup, copy `.env.example` to `.env` and set your values there. Load it before starting the app:

```bash
# macOS / Linux — export all vars from .env
export $(grep -v '^#' .env | xargs)
./mvnw spring-boot:run
```

> `.env` is gitignored — never commit it.

---

## Common Tasks

| Task | Command |
|---|---|
| Start backend | `./mvnw spring-boot:run` |
| Run all tests | `./mvnw test` |
| Start frontend dev | `cd frontend && npm run dev` |
| Build frontend | `cd frontend && npm run build` |
| View API docs | Open `http://localhost:8080/swagger-ui.html` |

