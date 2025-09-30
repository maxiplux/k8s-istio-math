# Kubernetes Istio Math - Eureka Server

A Spring Cloud Netflix Eureka Server implementation designed for service discovery in Kubernetes and Istio environments. This service acts as a registry where microservices can register themselves and discover other services in the distributed system.

## 📋 Table of Contents

- [Overview](#overview)
- [Technologies](#technologies)
- [Prerequisites](#prerequisites)
- [Project Structure](#project-structure)
- [Configuration](#configuration)
- [Building the Application](#building-the-application)
- [Running the Application](#running-the-application)
- [Docker Support](#docker-support)
- [Monitoring](#monitoring)
- [Port Configuration](#port-configuration)
- [License](#license)

## 🔍 Overview

This project provides a standalone Eureka Server instance that serves as a service registry for microservices architecture. It's particularly useful in Kubernetes environments where you want to maintain service discovery capabilities alongside container orchestration.

### Key Features

- **Service Discovery**: Centralized registry for microservice registration and discovery
- **Health Monitoring**: Built-in health checks and status monitoring
- **Metrics Export**: Prometheus metrics integration for observability
- **Standalone Configuration**: Configured to run as a standalone server without replication
- **Container-Ready**: Fully containerized with Docker support for multi-platform deployment

## 🛠 Technologies

- **Java**: 21
- **Spring Boot**: 3.2.4
- **Spring Cloud**: 2023.0.1
- **Spring Cloud Netflix Eureka Server**: Service discovery server
- **Spring Boot Actuator**: Application monitoring and management
- **Micrometer Prometheus**: Metrics registry for Prometheus integration
- **Gradle**: Build automation
- **Docker**: Containerization with multi-platform support (ARM64, AMD64)

## 📦 Prerequisites

To build and run this application, you need:

- **Java Development Kit (JDK)**: Version 21 or higher
- **Gradle**: 8.x (or use the included Gradle wrapper)
- **Docker**: (Optional) For containerized deployment
- **Docker Buildx**: (Optional) For multi-platform image builds

## 📁 Project Structure

```
k8s-istio-math/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── app/quantun/math/
│   │   │       └── MathApplication.java    # Main application class
│   │   └── resources/
│   │       └── application.properties      # Application configuration
│   └── test/
│       └── java/
│           └── app/quantun/math/
│               └── MathApplicationTests.java
├── gradle/                                  # Gradle wrapper files
├── build.gradle                             # Build configuration
├── settings.gradle                          # Gradle settings
├── Dockerfile                               # Docker image definition
├── publish.sh                               # Docker image publishing script
├── gradlew                                  # Gradle wrapper (Unix)
└── gradlew.bat                             # Gradle wrapper (Windows)
```

## ⚙️ Configuration

The application is configured through `application.properties`:

```properties
spring.application.name=eureka-server
server.port=${PORT:8045}
eureka.client.register-with-eureka=false
eureka.client.fetch-registry=false
```

### Configuration Details

- **Application Name**: `eureka-server`
- **Default Port**: `8045` (can be overridden with `PORT` environment variable)
- **Standalone Mode**: Configured not to register with itself or fetch registry

## 🔨 Building the Application

### Using Gradle Wrapper (Recommended)

**On Windows:**
```bash
.\gradlew.bat build
```

**On Unix/Linux/macOS:**
```bash
./gradlew build
```

### Using Gradle

```bash
gradle build
```

The build artifacts will be created in the `build/libs/` directory.

## 🚀 Running the Application

### Run Locally with Gradle

**On Windows:**
```bash
.\gradlew.bat bootRun
```

**On Unix/Linux/macOS:**
```bash
./gradlew bootRun
```

### Run the JAR directly

After building:
```bash
java -jar build/libs/math-0.0.1-SNAPSHOT.jar
```

### With Custom Port

```bash
PORT=9090 java -jar build/libs/math-0.0.1-SNAPSHOT.jar
```

### Access the Eureka Dashboard

Once the application is running, access the Eureka Dashboard at:
```
http://localhost:8045
```

## 🐳 Docker Support

### Building the Docker Image

The project includes a multi-stage Dockerfile for efficient image creation:

```bash
docker build -t eureka-server:latest .
```

### Running with Docker

```bash
docker run -p 8045:8080 eureka-server:latest
```

### Multi-Platform Build and Push

The project includes a `publish.sh` script for building and publishing multi-platform images:

```bash
bash publish.sh
```

Or manually:
```bash
docker buildx build --platform=linux/arm64,linux/amd64 --push --tag maxiplux/eureka-server:1.0.0 -f ./Dockerfile .
```

**Note**: The Docker container exposes port 8080 internally, but the application runs on port 8045 by default. Map ports accordingly.

### Running in Kubernetes

Example Kubernetes deployment:

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

## 📊 Monitoring

### Spring Boot Actuator

The application includes Spring Boot Actuator for monitoring and management. Access actuator endpoints at:

```
http://localhost:8045/actuator
```

Common endpoints:
- `/actuator/health` - Health check endpoint
- `/actuator/info` - Application information
- `/actuator/metrics` - Metrics endpoint
- `/actuator/prometheus` - Prometheus-formatted metrics

### Prometheus Metrics

Prometheus metrics are available at:
```
http://localhost:8045/actuator/prometheus
```

These metrics can be scraped by Prometheus for monitoring and alerting.

## 🔌 Port Configuration

- **Default Application Port**: `8045` (configurable via `PORT` environment variable)
- **Docker Internal Port**: `8080` (exposed by Dockerfile)
- **Recommended Production Port**: Configure as needed for your environment

When running in Docker, ensure proper port mapping:
```bash
docker run -p <host-port>:8080 -e PORT=8080 eureka-server:latest
```

## 📄 License

This project is part of the Quantun application suite. Please refer to your organization's licensing terms.

---

## 🤝 Contributing

For contributions, please follow the standard Git workflow:
1. Fork the repository
2. Create a feature branch
3. Commit your changes
4. Push to the branch
5. Create a Pull Request

## 📞 Support

For issues, questions, or contributions, please contact the development team or create an issue in the project repository.

---

**Built with ❤️ using Spring Cloud**
