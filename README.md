
# Spring Boot CRUD (PostgreSQL)

This is a simple Spring Boot CRUD application (Java 17, Spring Boot 3.x) using PostgreSQL as the database.

## What is included
- `Employee` entity with fields: `id`, `firstName`, `lastName`, `email`.
- REST endpoints under `/api/employees`:
  - `GET /api/employees` - list all employees
  - `GET /api/employees/{id}` - get employee by id
  - `POST /api/employees` - create employee (JSON body)
  - `PUT /api/employees/{id}` - update employee (JSON body)
  - `DELETE /api/employees/{id}` - delete employee

## Requirements
- Java 17+
- Maven 3.6+
- PostgreSQL (running locally or remotely)

## Setup (local PostgreSQL)
1. Create a database (example):
```sql
CREATE DATABASE demo_db;
CREATE USER demo_user WITH ENCRYPTED PASSWORD 'demo_pass';
GRANT ALL PRIVILEGES ON DATABASE demo_db TO demo_user;
```

2. Update `src/main/resources/application.properties` with your DB credentials:
```
spring.datasource.url=jdbc:postgresql://localhost:5432/demo_db
spring.datasource.username=demo_user
spring.datasource.password=demo_pass
```

3. Build & run:
```bash
mvn clean package
java -jar target/springboot-crud-postgres-0.0.1-SNAPSHOT.jar
```

The API will be available at `http://localhost:8080/api/employees`

## Notes
- `spring.jpa.hibernate.ddl-auto=update` will create/update tables automatically. For production consider migrations (Flyway/Liquibase).
- This project is intentionally minimal to be easy to run and learn from.
