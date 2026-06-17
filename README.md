# Task Manager App

## Overview

A RESTful task manager application built with Spring Boot. The application provides APIs to create, update, organize, search, and track tasks. It follows a layered architecture using Controllers, Services, Repositories, DTOs, and JPA entities.

## Features

- Create new tasks
- View all tasks
- View task details by ID
- Update existing task title, description, status, or due date
- Mark a task as completed
- Delete tasks
- Set priority levels for tasks
- Track task status (TODO, IN_PROGRESS, DONE)
- Assign task categories 
- Set due dates for tasks 
- Find overdue tasks
- Search tasks by title or title and category
- Use pagination and sorting for task lists
- Basic validation for task data using Jakarta Validation
- RESTful API structure
- Layered Spring Boot architecture
- Unit tests for Service and Controller layers

## Tech Stack

- Java 21
- Spring Boot
- Spring Web
- Spring Data JPA
- Hibernate
- Maven
- H2 Database
- Lombok
- JUnit 5 and Mockito

## Getting Started

### Prerequisites

Make sure you have the following installed:

- Java 17 or later
- Maven
- Git
- Docker, if running the app in a container
- A database, if using MySQL or PostgreSQL

### Installation

Clone the repository:

```bash
git clone https://github.com/your-username/task-manager.git
cd task-manager
```

Install dependencies:

```bash
mvn clean install
```

Run the application:

```bash
mvn spring-boot:run
```

The application should start at:

```text
http://localhost:8080
```

## Running Tests

Run the test suite with:

```bash
mvn test
```

## Configuration

Update `src/main/resources/application.properties` with your database settings.

```properties
spring.datasource.url=jdbc:h2:mem:taskdb
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=
spring.jpa.hibernate.ddl-auto=update
spring.h2.console.enabled=true
```

## API Endpoints

| Method | Endpoint                                                      | Description                    |
| --- |---------------------------------------------------------------|--------------------------------|
| `GET` | `/api/tasks`                                                  | Get all tasks                  |
| `GET` | `/api/tasks/search?title={keyword}`                           | Search tasks by title          |
| `GET` | `/api/tasks?page={page}&size={size}&sort={field},{direction}` | Get paginated and sorted tasks |
| `GET` | `/api/tasks/{id}`                                             | Get a task by ID               |
| `POST` | `/api/tasks`                                                  | Create a new task              |
| `PUT` | `/api/tasks/{id}`                                             | Update an existing task        |
| `PATCH` | `/api/tasks/{id}/complete`                                    | Mark a task as completed       |
| `DELETE` | `/api/tasks/{id}`                                             | Delete a task                  |

## Example Request

Create a task:

```bash
curl -X POST http://localhost:8080/api/tasks \
  -H "Content-Type: application/json" \
  -d '{
    "title": "Finish Spring Boot project",
    "description": "Complete task manager backend and README",
    "category": "Development",
    "priority": "HIGH",
    "status": "PENDING",
    "dueDate": "2026-06-20"
  }'
```

Example response:

```json
{
  "id": 1,
  "title": "Finish Spring Boot project",
  "description": "Complete task manager backend and README",
  "category": "Development",
  "priority": "HIGH",
  "status": "PENDING",
  "dueDate": "2026-06-20"
}
```

## Task Model

Example task fields:

| Field | Type | Description                                                |
| --- | --- |------------------------------------------------------------|
| `id` | Long | Unique task ID                                             |
| `title` | String | Task title                                                 |
| `description` | String | Task description                                           |
| `status` | String | Task status, such as `TODO` or `IN_PROGRESS` or `DONE`     |
| `priority` | String | Task priority, such as `LOW`, `MEDIUM`, or `HIGH`          |
| `category` | String | Optional Task category as `WORK`, `PERSONAL`, or `LEARING` |
| `dueDate` | LocalDate | due date                                                   |
| `createdAt` | LocalDateTime | Date and time when the task was created                    |
| `updatedAt` | LocalDateTime | Date and time when the task was last updated               |


## Future Improvements
- Run the application with Docker
- Add Swagger/OpenAPI documentation
- Add task reminders and notifications 
- Replace H2 with PostgreSQL 
- Add integration tests using MockMvc
