# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

This is a Spring Boot 3.4.4 test project using Java 17, focused on learning and testing Spring transaction behaviors, JPA operations, deserialization patterns, and distributed tracing with Micrometer. The project uses MySQL as the database and includes Docker Compose for local development.

## Build and Development Commands

### Build and Run
```bash
# Build the project
./gradlew build

# Run the application
./gradlew bootRun

# Create executable JAR
./gradlew bootJar
# Output: build/libs/spring-boot-test-0.0.1-SNAPSHOT.jar
```

### Testing
```bash
# Run all tests (note: ignoreFailures = true in build.gradle)
./gradlew test

# Run a specific test class
./gradlew test --tests "com.example.springboottest.service.RollbackTest"

# Run a specific test method
./gradlew test --tests "com.example.springboottest.service.RollbackTest.noRollbackFor_shouldBeIgnored"
```

### Docker
```bash
# Start MySQL database and application
docker-compose up -d

# Stop services
docker-compose down

# View logs
docker-compose logs -f app
```

## Architecture

### Package Structure
- `api/`: REST controllers and servlet filters
- `service/`: Business logic layer with transaction management examples
- `domain/`: JPA entities and repositories
- `data/`: DTOs and data transfer objects

### Key Architectural Patterns

**Transaction Testing Architecture**: The codebase extensively tests Spring transaction propagation and rollback behaviors:
- `CallerService` and `TargetService` demonstrate cross-service transaction propagation
- `OuterService`, `MiddleService`, `InnerService` demonstrate nested transaction behaviors
- Tests verify rollback-only marking when inner transactional methods throw exceptions, even when caught by outer methods

**Distributed Tracing**: Uses Micrometer Tracing with Brave bridge to automatically inject traceId and spanId into MDC (Mapped Diagnostic Context):
- `LoggingFilter` (currently disabled via comment) shows manual MDC tracing implementation
- Current approach relies on Micrometer's automatic MDC injection
- Log pattern configured in application.yml includes `TRACE_ID[%X{traceId:-}]SPAN_ID[%X{spanId:-}]`

**JPA Proxy Behavior**: Tests use `AopUtils.isCglibProxy()` to verify Spring proxy creation for transactional services, demonstrating understanding of Spring AOP and CGLIB proxies.

**Deserialization Patterns**: `DeserializationTest` documents various Jackson deserialization behaviors:
- Default constructor requirements
- Use of `@JsonCreator` for constructor-based deserialization
- Getter requirements for private fields
- `FAIL_ON_UNKNOWN_PROPERTIES` configuration

## Database Configuration

- **Local development**: MySQL at `localhost:3306/test` (username: root, password: 1234)
- **Docker environment**: Uses `jdbc:mysql://db:3306/test` with healthcheck
- JPA settings: `ddl-auto: update`, SQL logging enabled, `open-in-view: false`

## Testing Approach

This project uses extensive integration tests (`@SpringBootTest`) rather than unit tests to verify Spring framework behavior, particularly around:
- Transaction propagation and rollback scenarios
- Nested transaction handling with `UnexpectedRollbackException`
- JPA repository operations and entity lifecycle
- JSON deserialization edge cases

When adding new transaction-related code, follow the established pattern of creating test classes that verify both successful and rollback scenarios with explicit assertions on database state.
