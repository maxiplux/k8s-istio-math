# Math API Gateway

A Spring Cloud Gateway application that serves as an API Gateway for mathematical operation microservices, designed for Kubernetes and Istio service mesh deployment.

## Overview

This project implements an API Gateway using Spring Cloud Gateway that routes requests to backend mathematical operation services:
- **Addition and Subtraction Service** (`math-add-subtract`)
- **Division and Multiplication Service** (`math-division-multiplication`)

The gateway integrates with Netflix Eureka for service discovery and includes Prometheus metrics for monitoring through Spring Boot Actuator.

## Features

- **API Gateway Routing**: Routes HTTP requests to appropriate backend microservices
- **Service Discovery**: Integration with Netflix Eureka for dynamic service registration and discovery
- **Health Monitoring**: Spring Boot Actuator endpoints for health checks and metrics
- **Prometheus Metrics**: Built-in support for Prometheus monitoring
- **Multi-platform Docker Support**: Builds for both ARM64 and AMD64 architectures
- **Kubernetes/Istio Ready**: Designed for cloud-native deployment with service mesh support

## Prerequisites

- **Java 21** or higher
- **Gradle** (or use the included Gradle wrapper)
- **Docker** (for containerized deployment)
- **Docker Buildx** (for multi-platform builds)

## Architecture

The API Gateway routes requests to two backend services:

```
┌─────────────────┐
│   API Gateway   │ (Port 8080)
│  (This Service) │
└────────┬────────┘
         │
         ├──> /add-subtract/** ──────> math-add-subtract service (Port 8082)
         │
         └──> /division-multiplication/** ──> math-division-multiplication service (Port 8081)
```

## Building the Application

### Using Gradle Wrapper (Recommended)

```bash
# On Windows
.\gradlew build

# On Linux/Mac
./gradlew build
```

### Using Gradle

```bash
gradle build
```

The build will create a JAR file at: `build/libs/math-0.0.1-SNAPSHOT.jar`

## Running the Application

### Run Locally

```bash
java -jar build/libs/math-0.0.1-SNAPSHOT.jar
```

The gateway will start on port **8080** by default.

### Run with Custom Port

```bash
java -jar build/libs/math-0.0.1-SNAPSHOT.jar --server.port=9090
```

Or set the `PORT` environment variable:

```bash
export PORT=9090
java -jar build/libs/math-0.0.1-SNAPSHOT.jar
```

## Configuration

### Environment Variables

The application supports the following environment variables for configuration:

| Variable | Description | Default Value |
|----------|-------------|---------------|
| `PORT` | Server port | `8080` |
| `SERVER_ADD_SUBTRACT` | Add/Subtract service host | `math-add-subtract` |
| `SERVER_ADD_SUBTRACT_PORT` | Add/Subtract service port | `8082` |
| `SERVER_DIVISION_MULTIPLICATION` | Division/Multiplication service host | `math-division-multiplication` |
| `SERVER_DIVISION_MULTIPLICATION_PORT` | Division/Multiplication service port | `8081` |
| `EUREKA_SERVER` | Eureka server host | `localhost` |
| `EUREKA_PORT` | Eureka server port | `8045` |

### Gateway Routes

The gateway is configured with two routes:

1. **Addition/Subtraction Route**
   - Path: `/add-subtract/**`
   - Target: `http://math-add-subtract:8082`
   - Filter: StripPrefix=1

2. **Division/Multiplication Route**
   - Path: `/division-multiplication/**`
   - Target: `http://math-division-multiplication:8081`
   - Filter: StripPrefix=1

### Actuator Endpoints

Spring Boot Actuator endpoints are available for monitoring:

- Health: `http://localhost:8080/actuator/health`
- Metrics: `http://localhost:8080/actuator/metrics`
- Prometheus: `http://localhost:8080/actuator/prometheus`

## Docker Deployment

### Build Docker Image

Build a multi-platform Docker image (AMD64 and ARM64):

```bash
docker buildx build --platform=linux/arm64,linux/amd64 --tag maxiplux/api-gate-way:2.0.0 -f ./Dockerfile .
```

### Build and Push to Registry

Use the included publish script:

```bash
# On Linux/Mac
bash publish.sh

# On Windows (Git Bash or WSL)
bash publish.sh
```

Or manually:

```bash
docker buildx build --platform=linux/arm64,linux/amd64 --push --tag maxiplux/api-gate-way:2.0.0 -f ./Dockerfile .
```

### Run Docker Container

```bash
docker run -p 8080:8080 \
  -e EUREKA_SERVER=eureka-server \
  -e SERVER_ADD_SUBTRACT=math-add-subtract \
  -e SERVER_DIVISION_MULTIPLICATION=math-division-multiplication \
  maxiplux/api-gate-way:2.0.0
```

## Kubernetes Deployment

This application is designed to run in Kubernetes with Istio service mesh. A typical deployment would include:

### Example Deployment

```yaml
apiVersion: apps/v1
kind: Deployment
metadata:
  name: api-gateway
spec:
  replicas: 2
  selector:
    matchLabels:
      app: api-gateway
  template:
    metadata:
      labels:
        app: api-gateway
    spec:
      containers:
      - name: api-gateway
        image: maxiplux/api-gate-way:2.0.0
        ports:
        - containerPort: 8080
        env:
        - name: EUREKA_SERVER
          value: "eureka-server"
        - name: SERVER_ADD_SUBTRACT
          value: "math-add-subtract"
        - name: SERVER_DIVISION_MULTIPLICATION
          value: "math-division-multiplication"
```

### Example Service

```yaml
apiVersion: v1
kind: Service
metadata:
  name: api-gateway
spec:
  selector:
    app: api-gateway
  ports:
  - port: 8080
    targetPort: 8080
  type: LoadBalancer
```

## Testing

Run the test suite:

```bash
# Using Gradle wrapper
.\gradlew test

# Using Gradle
gradle test
```

## Development

### Prerequisites for Development

- Java 21 JDK
- IDE with Lombok support (IntelliJ IDEA, Eclipse with Lombok plugin, etc.)
- Docker for local containerization

### Running in Development Mode

The application includes Spring Boot DevTools for hot reloading during development:

```bash
.\gradlew bootRun
```

### Logging

Gateway routing is logged at TRACE level for debugging. Check the logs to see:
- Route matching and execution
- Service discovery events
- HTTP request/response details

## Technology Stack

- **Spring Boot 3.2.4** - Application framework
- **Spring Cloud Gateway** - API Gateway implementation
- **Spring Cloud Netflix Eureka Client** - Service discovery
- **Spring Boot Actuator** - Monitoring and health checks
- **Micrometer Prometheus Registry** - Metrics collection
- **Lombok** - Boilerplate code reduction
- **Java 21** - Programming language

## Project Information

- **Group**: `app.quantun`
- **Version**: `0.0.1-SNAPSHOT`
- **Spring Cloud Version**: `2023.0.1`

## CI/CD

The project includes a `Jenkinsfile` for CI/CD pipeline automation. Configure your Jenkins instance to use this pipeline for automated builds and deployments.

## License

This project is part of a microservices architecture demonstration for Kubernetes and Istio deployment.

## Contributing

When contributing to this project, please ensure:
1. All tests pass
2. Code follows the existing style conventions
3. Docker images build successfully for both platforms
4. Configuration changes are documented in this README

## Support

For issues and questions, please refer to the project's issue tracker or contact the development team.
