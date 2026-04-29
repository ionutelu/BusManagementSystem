# Bus Station Application — Documentation Index

> Generated: 2026-04-29 | Scan Level: Quick | Workflow: Initial Scan

---

## Project Overview

- **Type:** Monorepo with 2 parts (Backend + Frontend)
- **Primary Language:** Java 17 (Backend), TypeScript (Frontend)
- **Architecture:** Layered REST API + React SPA
- **Database:** MySQL (JPA/Hibernate)
- **API Docs:** Swagger UI at `http://localhost:8080/swagger-ui.html`

---

## Quick Reference

### Backend (`/src`)
- **Type:** Spring Boot REST API
- **Tech Stack:** Spring Boot 3.5.7, Java 17, Spring Data JPA, MySQL, SpringDoc OpenAPI
- **Entry Point:** `BusstationApplication.java`
- **Architecture:** Controller → Service → Repository → MySQL

### Frontend (`/frontend`)
- **Type:** React SPA (Vite)
- **Tech Stack:** React 19, TypeScript, TailwindCSS, React Query, Axios, React Router DOM
- **Entry Point:** `src/main.tsx` → `App.tsx`
- **Architecture:** Pages → API clients → Spring Boot REST

---

## Generated Documentation

| Document | Description |
|---|---|
| [Project Overview](./project-overview.md) | Executive summary, tech stack, domain model overview |
| [Source Tree Analysis](./source-tree-analysis.md) | Annotated directory tree for both parts |
| [Architecture — Backend](./architecture-backend.md) | Spring Boot layered architecture detail |
| [Architecture — Frontend](./architecture-frontend.md) | React SPA architecture detail |
| [API Contracts — Backend](./api-contracts-backend.md) | REST endpoint catalogue, DTO shapes, error format |
| [Data Models — Backend](./data-models-backend.md) | Entity relationships, enums, DB config |
| [Integration Architecture](./integration-architecture.md) | How frontend and backend communicate |
| [Development Guide](./development-guide.md) | Setup, run, test, build instructions |
| [⚡ Improvement Opportunities](./improvement-opportunities.md) | **17 actionable improvements ranked by priority** |

---

## Getting Started

```bash
# 1. Start MySQL on port 3307 with database 'busapp'
# 2. Start backend
./mvnw spring-boot:run

# 3. Start frontend
cd frontend && npm install && npm run dev

# 4. View API docs
open http://localhost:8080/swagger-ui.html
```

---

## Next Steps

When ready to plan new features or tackle improvements:

1. Review **[Improvement Opportunities](./improvement-opportunities.md)** — prioritised list of 17 improvements
2. Use `bmad-create-prd` to create a Product Requirements Document for specific improvements
3. Use `bmad-create-epics-and-stories` to break improvements into dev-ready stories
4. Use `bmad-agent-dev` (`Amelia`) to implement specific stories

