# WARP.md

This file provides guidance to WARP (warp.dev) when working with code in this repository.

## Project Overview

**Clinica Rodriguez Backend** - A Spring Boot REST API for clinic management built with Java 17 and Spring Boot 3.5.6.

- **Database**: MySQL (`bd_clinicarodriguez`)
- **Port**: Default Spring Boot (8080)
- **API Docs**: SpringDoc OpenAPI (Swagger UI available at `/swagger-ui.html`)

## Development Commands

### Build & Run
```powershell
# Clean and compile
./mvnw clean compile

# Run application
./mvnw spring-boot:run

# Package JAR
./mvnw clean package
```

### Testing
```powershell
# Run all tests
./mvnw test

# Run specific test class
./mvnw test -Dtest=ClinicarodriguezApplicationTests
```

### Database Setup
Ensure MySQL is running with the following configuration:
- **Host**: localhost:3306
- **Database**: bd_clinicarodriguez
- **Username**: root
- **Password**: Update in `src/main/resources/application.properties` (currently set to `password123`)

The application uses `spring.jpa.hibernate.ddl-auto=update` which will automatically create/update tables.

## Architecture

### Package Structure
```
com.clinicarodriguez.clinicarodriguez/
├── controller/       # REST endpoints (@RestController)
├── service/          # Business logic interfaces
│   └── impl/         # Service implementations
├── repository/       # JPA repositories (extends JpaRepository)
└── model/            # JPA entities (@Entity)
```

### Design Pattern
The codebase follows a **layered architecture**:
1. **Controller Layer**: Handles HTTP requests/responses, CORS configuration (`http://localhost:4200`)
2. **Service Layer**: Contains business logic (interface + implementation pattern)
3. **Repository Layer**: Data access using Spring Data JPA
4. **Model Layer**: JPA entities mapping to MySQL tables

### Key Conventions
- **Entity Naming**: Database columns use prefix pattern (e.g., `medi_` for Medicos entity)
- **Response Format**: Controllers return `HashMap<String, Object>` with `success`, `message`, and `data` keys
- **Repository Injection**: Controllers currently inject repositories directly (bypassing service layer - consider refactoring for consistency)
- **CORS**: Configured for Angular frontend at `http://localhost:4200`

### Current Entities
- **Medicos** (`medicos` table): Manages doctor information with fields for name, DNI, email, phone, photo URL, and status

## API Endpoints

Base URL: `http://localhost:8080/api`

### Medicos Resource (`/api/medicos`)
- `GET /medicos` - List all doctors
- `GET /medicos/{id}` - Get doctor by ID
- `POST /medicos` - Create new doctor
- `PUT /medicos/{id}` - Update doctor
- `DELETE /medicos/{id}` - Delete doctor

## Configuration Files

- **pom.xml**: Maven dependencies and build configuration
- **application.properties**: Database connection, JPA/Hibernate settings
- **nbactions.xml**: NetBeans IDE actions (run, debug, profile)

## Code Quality Notes

### Areas for Improvement
- Service layer exists but is unused - controllers inject repositories directly
- Variable naming inconsistency (e.g., `pacienteRepository` in MedicosServiceImpl but handles Medicos)
- Consider adding validation annotations to model fields
- Add exception handling with `@ControllerAdvice`
- Implement DTOs to separate API contracts from database entities

### When Adding New Features
1. Create entity in `model/` package with JPA annotations
2. Create repository interface extending `JpaRepository`
3. Create service interface and implementation in `service/` and `service/impl/`
4. Create REST controller in `controller/` package
5. Use the existing response format pattern for consistency
6. Update CORS origins if frontend URL changes

