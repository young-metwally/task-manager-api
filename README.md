# Task Manager API

A Spring Boot REST API for task management built following full SDLC best practices.

## Quality

## Quality

[![Reliability Rating](https://sonarcloud.io/api/project_badges/measure?project=young-metwally_task-manager-api&metric=reliability_rating)](https://sonarcloud.io/summary/new_code?id=young-metwally_task-manager-api)
[![Security Rating](https://sonarcloud.io/api/project_badges/measure?project=young-metwally_task-manager-api&metric=security_rating)](https://sonarcloud.io/summary/new_code?id=young-metwally_task-manager-api)
[![Maintainability Rating](https://sonarcloud.io/api/project_badges/measure?project=young-metwally_task-manager-api&metric=sqale_rating)](https://sonarcloud.io/summary/new_code?id=young-metwally_task-manager-api)

## Tech Stack
- Java 17 + Spring Boot 3.5
- JUnit 5 + Mockito (TDD)
- H2 Database
- Azure DevOps CI/CD Pipeline
- SonarCloud code quality
- Swagger OpenAPI docs

## API Endpoints
| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | /api/v1/tasks | Create a task |
| GET | /api/v1/tasks | Get all tasks |
| PUT | /api/v1/tasks/{id}/complete | Complete a task |
| DELETE | /api/v1/tasks/{id} | Delete a task |

## Documentation
Swagger UI: http://localhost:8080/swagger-ui/index.html