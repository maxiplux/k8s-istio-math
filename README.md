# Math Microservice for Kubernetes & Istio

A Spring Boot microservice designed for deployment on Kubernetes with Istio service mesh. This application provides a foundation for mathematical operations with built-in observability, monitoring, and API documentation features.

## Overview

This is a cloud-native microservice built with Spring Boot 3.2.4 and Java 21, specifically designed to run in containerized environments with Kubernetes and Istio. The service includes production-ready features such as health checks, metrics collection, and OpenAPI documentation.

## Features

- **RESTful API**: Spring Boot Web for building REST endpoints
- **Health Monitoring**: Spring Boot Actuator for health checks and application metrics
- **Metrics Export**: Prometheus integration for metrics collection and monitoring
- **API Documentation**: OpenAPI/Swagger UI for interactive API documentation
- **Cloud Native**: Designed for Kubernetes deployment with Istio service mesh
- **Multi-Architecture Support**: Docker images built for both ARM64 and AMD64 architectures
- **Development Tools**: Spring Boot DevTools for enhanced development experience

## Technology Stack

- **Java**: 21
- **Spring Boot**: 3.2.4
- **Build Tool**: Gradle
- **Containerization**: Docker with multi-stage builds
- **Observability**: 
  - Spring Boot Actuator
  - Micrometer with Prometheus registry
- **API Documentation**: SpringDoc OpenAPI 2.3.0
- **Utilities**: Lombok

## Prerequisites

Before you begin, ensure you have the following installed:

- **Java Development Kit (JDK) 21** or higher
- **Gradle** (or use the included Gradle wrapper)
- **Docker** (for containerization)
- **Docker Buildx** (for multi-platform builds)
- **Kubernetes cluster** (for deployment)
- **Istio** (optional, for service mesh features)

## Getting Started

### Local Development

#### Build the Application

Using Gradle wrapper (recommended):

```bash
# On Windows
gradlew.bat build

# On Linux/Mac
./gradlew build
```

Or using your installed Gradle:

```bash
gradle build
```

#### Run the Application

```bash
# On Windows
gradlew.bat bootRun

# On Linux/Mac
./gradlew bootRun
```

The application will start on port 8080 (configurable via `PORT` environment variable).

#### Run Tests

```bash
# On Windows
gradlew.bat test

# On Linux/Mac
./gradlew test
```

### Docker Deployment

#### Build Docker Image

Build the Docker image using the provided Dockerfile:

```bash
docker build -t math-add-subtract:latest .
```

#### Build Multi-Architecture Images

For production deployment supporting both ARM64 and AMD64 architectures:

```bash
docker buildx build --platform=linux/arm64,linux/amd64 --push --tag maxiplux/math-add-subtract:1.0.0 -f ./Dockerfile .
```

Or use the provided publish script:

```bash
# On Windows
.\publish.sh

# On Linux/Mac
./publish.sh
```

#### Run Docker Container

```bash
docker run -p 8080:8080 math-add-subtract:latest
```

### Kubernetes Deployment

This application is designed to be deployed on Kubernetes with Istio service mesh. The Docker image is optimized for cloud-native deployments.

Example Kubernetes deployment (create your own YAML manifests):

```yaml
apiVersion: apps/v1
kind: Deployment
metadata:
  name: math-service
spec:
  replicas: 3
  selector:
    matchLabels:
      app: math-service
  template:
    metadata:
      labels:
        app: math-service
    spec:
      containers:
      - name: math-service
        image: maxiplux/math-add-subtract:1.0.0
        ports:
        - containerPort: 8080
```

## API Documentation

Once the application is running, you can access:

- **Swagger UI**: http://localhost:8080/swagger-ui.html
- **OpenAPI JSON**: http://localhost:8080/v3/api-docs
- **Health Check**: http://localhost:8080/actuator/health
- **Prometheus Metrics**: http://localhost:8080/actuator/prometheus

## Configuration

The application can be configured using environment variables or `application.properties`:

- `PORT`: Server port (default: 8080)
- `spring.application.name`: Application name (default: math-add-subtract)

Additional Spring Boot and Actuator configurations can be added to `src/main/resources/application.properties`.

## Project Structure

```
k8s-istio-math/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── app/quantun/math/
│   │   │       └── MathApplication.java
│   │   └── resources/
│   │       └── application.properties
│   └── test/
│       └── java/
│           └── app/quantun/math/
│               └── MathApplicationTests.java
├── gradle/
│   └── wrapper/
├── build.gradle
├── settings.gradle
├── Dockerfile
├── publish.sh
├── gradlew
├── gradlew.bat
└── README.md
```

## Monitoring and Observability

The application exposes several actuator endpoints for monitoring:

- `/actuator/health` - Application health status
- `/actuator/info` - Application information
- `/actuator/metrics` - Available metrics
- `/actuator/prometheus` - Prometheus-formatted metrics

These endpoints are essential for Kubernetes liveness/readiness probes and Prometheus monitoring in an Istio service mesh.

## Development

### Adding New Features

1. Create REST controllers in the `app.quantun.math` package
2. Add service layer classes for business logic
3. Update `application.properties` for new configurations
4. Add tests in the `test` directory

### Code Quality

The project uses:
- **Lombok** to reduce boilerplate code
- **Spring Boot DevTools** for automatic restart during development
- **JUnit Platform** for testing

## License

Please refer to your organization's license policy.

## Support

For issues, questions, or contributions, please contact the development team.

## Version History

- **0.0.1-SNAPSHOT**: Initial development version
- **1.0.0**: Production-ready release with multi-architecture support
