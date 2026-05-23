# Online Course Platform Backend

Spring Boot REST API final project with PostgreSQL, layered architecture, DTOs, validation, exception handling, JWT security, file upload/download, async processes, Swagger UI, logging, Dockerfile and docker-compose.

Author prefix in classes: `BaltabayAssel`.

## Run with Docker

```bash
docker compose up --build
```

API: `http://localhost:8080`

Swagger UI: `http://localhost:8080/swagger-ui.html`

Health check: `http://localhost:8080/actuator/health`

## Demo Flow

1. Register admin or user: `POST /api/auth/register`
2. Login: `POST /api/auth/login`
3. Use `Authorization: Bearer <token>` for protected endpoints.
4. Create courses, lessons, assignments, enrollments and submissions.
5. Upload files with `POST /api/files/upload`, download with `GET /api/files/{id}/download`.

## Important Requirement Coverage

- 7 entities: user, course, lesson, enrollment, assignment, submission, file attachment.
- Controller, service and repository layers.
- RESTful GET, POST, PUT, DELETE endpoints.
- Path and query parameters.
- PostgreSQL via Spring Data JPA.
- DTO classes and manual mappers.
- Validation and global exception handling.
- Pagination, sorting, searching and filtering on `GET /api/courses`.
- Spring Security with registration, authentication, authorization and JWT.
- File upload/download.
- Async processes with `@Async` and `CompletableFuture`.
- Swagger UI with springdoc-openapi.
- Request/error/action logging.
- Dockerfile, docker-compose, multi-stage build and healthcheck.
