# Spring PetClinic REST API

A modern RESTful API implementation of the classic Spring PetClinic application. Transformed from a monolithic server-side rendered application into a high-performance, scalable API built with Java 21 and Spring Boot 3, following industry best practices.

## Key Features

This project delivers a fully refactored backend-first implementation:

- **Domain-Driven Architecture** - Clear separation of concerns with Owner, Pet, Visit, and Vet domains
- **Complete CRUD Operations** - Full POST, GET, PUT, and DELETE implementations for all entities
- **Data Pagination** - Spring Data Pageable integration for the Owners endpoint to ensure optimal performance with large datasets
- **Interactive API Documentation** - Swagger UI (OpenAPI 3) for real-time testing and exploration
- **Modern Java** - Java Records for DTOs (Data Transfer Objects) ensuring immutability and clean code
- **Global Exception Handling** - Standardized JSON error responses for 404 Not Found and 400 Bad Request (Validation) scenarios
- **Production-Ready** - Comprehensive logging, validation, and best practices throughout

## Technology Stack

| Component | Version |
|-----------|---------|
| **Language** | Java 21 |
| **Framework** | Spring Boot 3.2.4 |
| **Persistence** | Spring Data JPA / Hibernate |
| **Database** | MySQL 9.5 (H2 support for development) |
| **API Documentation** | SpringDoc OpenAPI |
| **Build Tool** | Maven |

## Project Structure

The project follows a **Domain Slicing** pattern for improved maintainability and scalability:

```
src/main/java/org/springframework/samples/petclinic/
├── owner/   # Owner management domain
├── pet/     # Pet and pet type management
├── visit/   # Medical visit history
├── vet/     # Veterinarian and specialty management
└── dto/     # Data Transfer Objects (Request/Response Records)
```

## Getting Started

### Prerequisites

- Java 21 or higher
- Docker (optional, for MySQL)

### 1. Clone the Repository

```bash
git clone https://github.com/spring-projects/spring-petclinic.git
cd spring-petclinic
```

### 2. Start MySQL (Recommended)

```bash
docker compose up mysql
```

### 3. Run the Application

```bash
./mvnw spring-boot:run
```

The application will start on `http://localhost:9090`

## API Documentation

The entire API is documented and testable via Swagger UI:

**Swagger UI:** [http://localhost:9090/swagger-ui.html](http://localhost:9090/swagger-ui.html)

### API Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/v1/owners?page=0&size=10` | List owners with pagination |
| POST | `/api/v1/owners` | Create a new owner |
| GET | `/api/v1/owners/{id}` | Get owner by ID |
| PUT | `/api/v1/owners/{id}` | Update owner details |
| DELETE | `/api/v1/owners/{id}` | Delete an owner |
| GET | `/api/v1/pets` | List all pets |
| POST | `/api/v1/pets` | Register a new pet |
| PUT | `/api/v1/visits/{id}` | Update visit details |
| DELETE | `/api/v1/vets/{id}` | Remove a veterinarian |

## Building and Testing

### Build the Project

```bash
./mvnw clean package
```

### Run Tests

```bash
./mvnw test
```

### Run with Different Databases

**H2 (In-Memory - Development):**
```bash
./mvnw spring-boot:run
```

**MySQL:**
```bash
docker compose up mysql
./mvnw spring-boot:run -Dspring.profiles.active=mysql
```

**PostgreSQL:**
```bash
docker compose up postgres
./mvnw spring-boot:run -Dspring.profiles.active=postgres
```

## Docker Support

### Build Docker Image

```bash
docker build -t spring-petclinic .
```

### Run with Docker Compose

```bash
docker compose up
```

This will start both the application and MySQL database.

## Contributing

Contributions are welcome! Please feel free to:
- Open issues for bugs or feature requests
- Submit pull requests with improvements
- Suggest enhancements to the architecture or code quality

## License

See [LICENSE.txt](LICENSE.txt) for details.

---

**Happy coding!** If you find this project helpful, please give it a star!
