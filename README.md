# Math Microservices for Kubernetes & Istio

A comprehensive Spring Boot microservices ecosystem designed for deployment on Kubernetes with Istio service mesh. This project includes multiple services for mathematical operations with built-in service discovery, API gateway routing, observability, monitoring, and API documentation features.

## 📋 Table of Contents

- [Overview](#overview)
- [Architecture](#architecture)
- [Services](#services)
  - [Math Microservice (Add/Subtract)](#math-microservice-addsubtract)
  - [Eureka Server](#eureka-server)
  - [API Gateway](#api-gateway)
- [Features](#features)
- [Technology Stack](#technology-stack)
- [Prerequisites](#prerequisites)
- [Getting Started](#getting-started)
  - [Building All Services](#building-all-services)
  - [Running Services Locally](#running-services-locally)
- [Service-Specific Documentation](#service-specific-documentation)
  - [Math Add/Subtract Service](#math-addsubtract-service)
  - [Eureka Server Setup](#eureka-server-setup)
  - [API Gateway Setup](#api-gateway-setup)
- [Docker Deployment](#docker-deployment)
- [Kubernetes Deployment](#kubernetes-deployment)
- [Monitoring and Observability](#monitoring-and-observability)
- [API Documentation](#api-documentation)
- [Contributing](#contributing)
- [License](#license)

## Overview

This is a cloud-native microservices architecture built with Spring Boot 3.2.4 and Java 21, specifically designed to run in containerized environments with Kubernetes and Istio. The ecosystem includes:

1. **Mathematical Operation Services**: RESTful services providing math operations
2. **Eureka Server**: Service discovery and registration
3. **API Gateway**: Central routing and load balancing

All services are production-ready with health checks, metrics collection, and multi-architecture Docker support.

## Architecture

The microservices architecture follows this design:

```
┌─────────────────────────────────────────────────────────────┐
│                      Kubernetes Cluster                      │
│                                                               │
│  ┌────────────────┐         ┌──────────────────┐            │
│  │ Eureka Server  │◄────────┤  API Gateway     │            │
│  │   (Port 8045)  │         │   (Port 8080)    │            │
│  └────────────────┘         └────────┬─────────┘            │
│         ▲                             │                       │
│         │                             │                       │
│         │         ┌───────────────────┴─────────────┐        │
│         │         │                                 │        │
│         │         ▼                                 ▼        │
│  ┌──────┴────────────────┐           ┌──────────────────┐   │
│  │  Math Add/Subtract    │           │ Math Division/   │   │
│  │  Service (Port 8082)  │           │ Multiplication   │   │
│  │                       │           │ (Port 8081)      │   │
│  └───────────────────────┘           └──────────────────┘   │
│                                                               │
└───────────────────────────────────────────────────────────────┘
```

### Request Flow

1. **External requests** → API Gateway (Port 8080)
2. **API Gateway** routes to:
   - `/add-subtract/**` → Math Add/Subtract Service
   - `/division-multiplication/**` → Math Division/Multiplication Service
3. **All services** register with Eureka Server for service discovery
4. **Istio service mesh** provides observability, traffic management, and security

## Services

### Math Microservice (Add/Subtract)

The core mathematical operations service providing addition and subtraction capabilities.

- **Port**: 8080 (default, configurable)
- **Docker Image**: `maxiplux/math-add-subtract:1.0.0`
- **Branch**: `master` / `math-add-subtract`

### Eureka Server

Netflix Eureka Server implementation for service discovery and registration.

- **Port**: 8045 (default)
- **Docker Image**: `maxiplux/eureka-server:1.0.0`
- **Branch**: `eureka-server`
- **Dashboard**: `http://localhost:8045`

### API Gateway

Spring Cloud Gateway for routing and load balancing across microservices.

- **Port**: 8080
- **Docker Image**: `maxiplux/api-gate-way:2.0.0`
- **Branch**: `api-gate-way`

## Features

### Core Features (All Services)

- **RESTful APIs**: Spring Boot Web for building REST endpoints
- **Health Monitoring**: Spring Boot Actuator for health checks and application metrics
- **Metrics Export**: Prometheus integration for metrics collection and monitoring
- **Cloud Native**: Designed for Kubernetes deployment with Istio service mesh
- **Multi-Architecture Support**: Docker images built for both ARM64 and AMD64 architectures
- **Development Tools**: Spring Boot DevTools for enhanced development experience

### Service-Specific Features

#### Math Services
- OpenAPI/Swagger UI for interactive API documentation
- Mathematical operation endpoints

#### Eureka Server
- Service discovery and registration
- Standalone configuration (no replication)
- Web-based dashboard for monitoring registered services

#### API Gateway
- Dynamic routing based on service discovery
- Load balancing across service instances
- Request forwarding with path rewriting
- Integration with Eureka for service lookup

## Technology Stack

- **Java**: 21
- **Spring Boot**: 3.2.4
- **Spring Cloud**: 2023.0.1
- **Build Tool**: Gradle
- **Containerization**: Docker with multi-stage builds
- **Service Discovery**: Spring Cloud Netflix Eureka
- **API Gateway**: Spring Cloud Gateway
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

### Building All Services

Each service can be built independently using Gradle wrapper:

```bash
# On Windows
gradlew.bat build

# On Linux/Mac
./gradlew build
```

Build artifacts will be created in the `build/libs/` directory.

### Running Services Locally

#### Start Services in Order:

1. **Eureka Server** (must start first):
```bash
# Set custom port if needed
export PORT=8045
./gradlew bootRun
```

2. **Math Services**:
```bash
# Math Add/Subtract (port 8082)
export PORT=8082
./gradlew bootRun

# Math Division/Multiplication (port 8081)
export PORT=8081
./gradlew bootRun
```

3. **API Gateway** (start last):
```bash
export PORT=8080
./gradlew bootRun
```

## Service-Specific Documentation

### Math Add/Subtract Service

#### Configuration

The application can be configured using environment variables or `application.properties`:

- `PORT`: Server port (default: 8080)
- `spring.application.name`: Application name (default: math-add-subtract)

#### Running the Service

```bash
# Using Gradle wrapper
./gradlew bootRun

# Or run JAR directly
java -jar build/libs/math-0.0.1-SNAPSHOT.jar
```

#### Testing

```bash
./gradlew test
```

#### API Endpoints

Once running, access:
- **Swagger UI**: http://localhost:8080/swagger-ui.html
- **OpenAPI JSON**: http://localhost:8080/v3/api-docs
- **Health Check**: http://localhost:8080/actuator/health
- **Prometheus Metrics**: http://localhost:8080/actuator/prometheus

### Eureka Server Setup

#### Configuration

The Eureka Server is configured through `application.properties`:

```properties
spring.application.name=eureka-server
server.port=${PORT:8045}
eureka.client.register-with-eureka=false
eureka.client.fetch-registry=false
```

**Configuration Details:**
- **Application Name**: `eureka-server`
- **Default Port**: `8045` (can be overridden with `PORT` environment variable)
- **Standalone Mode**: Configured not to register with itself or fetch registry

#### Running Eureka Server

**Using Gradle Wrapper:**

```bash
# On Windows
.\gradlew.bat bootRun

# On Unix/Linux/macOS
./gradlew bootRun
```

**Using JAR:**

```bash
java -jar build/libs/math-0.0.1-SNAPSHOT.jar
```

**With Custom Port:**

```bash
PORT=9090 java -jar build/libs/math-0.0.1-SNAPSHOT.jar
```

#### Accessing Eureka Dashboard

Once the application is running, access the Eureka Dashboard at:
```
http://localhost:8045
```

The dashboard shows all registered services, their status, and health information.

#### Docker Deployment

**Build Image:**
```bash
docker build -t eureka-server:latest .
```

**Run Container:**
```bash
docker run -p 8045:8080 eureka-server:latest
```

**Multi-Platform Build:**
```bash
docker buildx build --platform=linux/arm64,linux/amd64 --push --tag maxiplux/eureka-server:1.0.0 -f ./Dockerfile .
```

**Note**: The Docker container exposes port 8080 internally, but the application runs on port 8045 by default. Map ports accordingly.

#### Kubernetes Deployment Example

```yaml
apiVersion: apps/v1
kind: Deployment
metadata:
  name: eureka-server
spec:
  replicas: 1
  selector:
    matchLabels:
      app: eureka-server
  template:
    metadata:
      labels:
        app: eureka-server
    spec:
      containers:
      - name: eureka-server
        image: maxiplux/eureka-server:1.0.0
        ports:
        - containerPort: 8080
        env:
        - name: PORT
          value: "8080"
---
apiVersion: v1
kind: Service
metadata:
  name: eureka-server
spec:
  selector:
    app: eureka-server
  ports:
  - protocol: TCP
    port: 8080
    targetPort: 8080
  type: ClusterIP
```

### API Gateway Setup

#### Configuration

The API Gateway supports the following environment variables:

| Variable | Description | Default Value |
|----------|-------------|---------------|
| `PORT` | Server port | `8080` |
| `SERVER_ADD_SUBTRACT` | Add/Subtract service host | `math-add-subtract` |
| `SERVER_ADD_SUBTRACT_PORT` | Add/Subtract service port | `8082` |
| `SERVER_DIVISION_MULTIPLICATION` | Division/Multiplication service host | `math-division-multiplication` |
| `SERVER_DIVISION_MULTIPLICATION_PORT` | Division/Multiplication service port | `8081` |
| `EUREKA_SERVER` | Eureka server host | `localhost` |
| `EUREKA_PORT` | Eureka server port | `8045` |

#### Gateway Routes

The gateway is configured with two main routes:

1. **Addition/Subtraction Route**
   - Path: `/add-subtract/**`
   - Target: `http://math-add-subtract:8082`
   - Filter: StripPrefix=1

2. **Division/Multiplication Route**
   - Path: `/division-multiplication/**`
   - Target: `http://math-division-multiplication:8081`
   - Filter: StripPrefix=1

#### Running API Gateway

**Using Gradle:**
```bash
./gradlew bootRun
```

**Using JAR:**
```bash
java -jar build/libs/math-0.0.1-SNAPSHOT.jar
```

**With Custom Configuration:**
```bash
export EUREKA_SERVER=eureka-server
export SERVER_ADD_SUBTRACT=math-add-subtract
java -jar build/libs/math-0.0.1-SNAPSHOT.jar
```

#### Docker Deployment

**Build Image:**
```bash
docker buildx build --platform=linux/arm64,linux/amd64 --tag maxiplux/api-gate-way:2.0.0 -f ./Dockerfile .
```

**Run Container:**
```bash
docker run -p 8080:8080 \
  -e EUREKA_SERVER=eureka-server \
  -e SERVER_ADD_SUBTRACT=math-add-subtract \
  -e SERVER_DIVISION_MULTIPLICATION=math-division-multiplication \
  maxiplux/api-gate-way:2.0.0
```

#### Kubernetes Deployment Example

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
---
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

## Docker Deployment

### Building Docker Images

All services include Docker support with multi-stage builds for efficiency.

#### Build Single Service

```bash
docker build -t <service-name>:latest .
```

#### Build Multi-Architecture Images

For production deployment supporting both ARM64 and AMD64 architectures:

```bash
docker buildx build --platform=linux/arm64,linux/amd64 --push --tag <username>/<service-name>:<version> -f ./Dockerfile .
```

#### Using Publish Scripts

Each service may include a `publish.sh` script:

```bash
# On Windows (Git Bash/WSL)
bash publish.sh

# On Linux/Mac
./publish.sh
```

### Running with Docker Compose

Create a `docker-compose.yml` for local development:

```yaml
version: '3.8'
services:
  eureka-server:
    image: maxiplux/eureka-server:1.0.0
    ports:
      - "8045:8080"
    environment:
      - PORT=8080

  math-add-subtract:
    image: maxiplux/math-add-subtract:1.0.0
    ports:
      - "8082:8082"
    environment:
      - PORT=8082
      - EUREKA_SERVER=eureka-server
    depends_on:
      - eureka-server

  api-gateway:
    image: maxiplux/api-gate-way:2.0.0
    ports:
      - "8080:8080"
    environment:
      - EUREKA_SERVER=eureka-server
      - SERVER_ADD_SUBTRACT=math-add-subtract
    depends_on:
      - eureka-server
      - math-add-subtract
```

## Kubernetes Deployment

This microservices ecosystem is designed to be deployed on Kubernetes with Istio service mesh.

### Deployment Order

1. Deploy Eureka Server first
2. Deploy Math services (they will register with Eureka)
3. Deploy API Gateway last

### Complete Deployment Example

```yaml
# Eureka Server
apiVersion: apps/v1
kind: Deployment
metadata:
  name: eureka-server
spec:
  replicas: 1
  selector:
    matchLabels:
      app: eureka-server
  template:
    metadata:
      labels:
        app: eureka-server
    spec:
      containers:
      - name: eureka-server
        image: maxiplux/eureka-server:1.0.0
        ports:
        - containerPort: 8080
---
apiVersion: v1
kind: Service
metadata:
  name: eureka-server
spec:
  selector:
    app: eureka-server
  ports:
  - port: 8080
    targetPort: 8080
---
# Math Service
apiVersion: apps/v1
kind: Deployment
metadata:
  name: math-add-subtract
spec:
  replicas: 3
  selector:
    matchLabels:
      app: math-add-subtract
  template:
    metadata:
      labels:
        app: math-add-subtract
    spec:
      containers:
      - name: math-service
        image: maxiplux/math-add-subtract:1.0.0
        ports:
        - containerPort: 8080
---
apiVersion: v1
kind: Service
metadata:
  name: math-add-subtract
spec:
  selector:
    app: math-add-subtract
  ports:
  - port: 8082
    targetPort: 8080
---
# API Gateway
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
---
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

## Monitoring and Observability

All services expose Spring Boot Actuator endpoints for monitoring:

### Common Actuator Endpoints

- `/actuator/health` - Application health status
- `/actuator/info` - Application information
- `/actuator/metrics` - Available metrics
- `/actuator/prometheus` - Prometheus-formatted metrics

### Service-Specific Endpoints

#### Math Services
- **Health**: `http://localhost:8080/actuator/health`
- **Prometheus**: `http://localhost:8080/actuator/prometheus`

#### Eureka Server
- **Dashboard**: `http://localhost:8045`
- **Health**: `http://localhost:8045/actuator/health`
- **Prometheus**: `http://localhost:8045/actuator/prometheus`

#### API Gateway
- **Health**: `http://localhost:8080/actuator/health`
- **Prometheus**: `http://localhost:8080/actuator/prometheus`
- **Gateway Routes**: `http://localhost:8080/actuator/gateway/routes`

### Prometheus Integration

All services are configured with Micrometer Prometheus registry. Configure Prometheus to scrape metrics:

```yaml
scrape_configs:
  - job_name: 'eureka-server'
    static_configs:
      - targets: ['eureka-server:8080']
    metrics_path: '/actuator/prometheus'
  
  - job_name: 'api-gateway'
    static_configs:
      - targets: ['api-gateway:8080']
    metrics_path: '/actuator/prometheus'
  
  - job_name: 'math-services'
    static_configs:
      - targets: ['math-add-subtract:8082']
    metrics_path: '/actuator/prometheus'
```

### Istio Service Mesh

When deployed with Istio, you get additional observability features:
- Distributed tracing with Jaeger
- Service mesh metrics with Kiali
- Request visualization and monitoring

## API Documentation

### Math Services

Access OpenAPI/Swagger documentation:

- **Swagger UI**: http://localhost:8080/swagger-ui.html
- **OpenAPI JSON**: http://localhost:8080/v3/api-docs

### API Gateway Routes

The gateway exposes routes for all backend services:

- **Add/Subtract Operations**: `http://localhost:8080/add-subtract/*`
- **Division/Multiplication Operations**: `http://localhost:8080/division-multiplication/*`

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
├── README.md
├── README-eureka-server.md
└── readme-api-gateway.md
```

## Development

### Adding New Features

1. Create REST controllers in the `app.quantun.math` package
2. Add service layer classes for business logic
3. Update `application.properties` for new configurations
4. Add tests in the `test` directory
5. Update relevant README documentation

### Code Quality

The project uses:
- **Lombok** to reduce boilerplate code
- **Spring Boot DevTools** for automatic restart during development
- **JUnit Platform** for testing

### Development Workflow

1. Start Eureka Server
2. Start your service with DevTools enabled
3. Service auto-registers with Eureka
4. Make changes (auto-reload with DevTools)
5. Test via API Gateway or direct service calls

## Contributing

When contributing to this project, please ensure:

1. All tests pass (`./gradlew test`)
2. Code follows existing style conventions
3. Docker images build successfully for both platforms
4. Configuration changes are documented in README files
5. Follow the standard Git workflow:
   - Fork the repository
   - Create a feature branch
   - Commit your changes
   - Push to the branch
   - Create a Pull Request

## License

Please refer to your organization's license policy.

## Support

For issues, questions, or contributions, please contact the development team or create an issue in the project repository.

## Version History

### Math Add/Subtract Service
- **0.0.1-SNAPSHOT**: Initial development version
- **1.0.0**: Production-ready release with multi-architecture support

### Eureka Server
- **1.0.0**: Standalone Eureka Server with Kubernetes support

### API Gateway
- **0.0.1-SNAPSHOT**: Initial development version
- **2.0.0**: Production-ready gateway with dynamic routing

---

**Built with ❤️ using Spring Boot and Spring Cloud**
