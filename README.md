## MyAPI - Spring Boot REST API

Welcome to the MyAPI project! This is a Spring Boot-based REST API designed with best practices. The project revolves around a single entity, **Product**, for simplicity and to demonstrate a modular setup with proper separation of concerns.

### Project Structure
```plaintext
├───src 
│   ├───main
│   │   ├───java/com/example/myapi
│   │   │   ├───advice         # Custom exception handling and controllers' advices
│   │   │   ├───controller     # REST controllers for handling API endpoints
│   │   │   ├───dto            # Data Transfer Objects (DTOs) for request and response bodies
│   │   │   ├───exception      # Custom exceptions for handling specific error cases
│   │   │   ├───mapper         # Mappers for converting between models and DTOs
│   │   │   ├───model          # Entity models, including the Product entity
│   │   │   ├───repo           # Repositories for database access and CRUD operations
│   │   │   └───service        # Services containing business logic for entities
│   │   └───resources
│   │       ├───static         # Static resources
│   │       └───templates      # Templates (if required for any future frontend rendering)
│   └───test
│       └───java/com/example/myapi
│           └───service        # Unit tests for services
```

### Technologies Used

* **Spring Boot** - Framework for building REST APIs
* **Spring Data JPA** - For database operations
* **ModelMapper** - For DTO to entity mapping
* **H2 Database** - In-memory database for local testing
* **JUnit 5** - Unit testing framework
* **Spring Boot - SLF4J** - Logging
* **Maven** - Project management and build tool


### Getting Started

#### Prerequisites

Ensure you have the following installed:
* Java 21
* Maven 3.8 or higher

#### Running the Application
1. Clone the repository:
```bash
git clone https://github.com/aalkann/Spring-Boot-REST-API.git
```
2. Navigate to the project directory:
```bash
cd Spring-Boot-REST-API
```
3. Build and run the application:
```bash
mvn spring-boot:run
```
The application will start at http://localhost:8080.

### API Endpoints

#### Product Endpoints

| HTTP Method | Endpoint               | Description                                                    |
|-------------|------------------------|----------------------------------------------------------------|
| GET         | `/api/products`        | Retrieve all products                                          |
| GET         | `/api/products/filter` | Retrieve all products by name and category using pagination    |
| GET         | `/api/products/search` | Retrieve all products by keyword using pagination              |
| GET         | `/api/products/{id}`   | Retrieve a product by ID                                       |
| POST        | `/api/products`        | Create a new product                                           |
| PUT         | `/api/products`        | Update a product by ID                                         |
| DELETE      | `/api/products/{id}`   | Delete a product by ID                                         |

### Exception Handling
Custom exception handling is implemented to manage errors. The ``advice`` package contains a centralized exception handling mechanism using ``@ControllerAdvice`` for consistency across all API responses

### Testing
Unit tests are written with JUnit 5 and focus on the service layer. To run the tests:
```bash
mvn test
```