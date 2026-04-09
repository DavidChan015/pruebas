# Build stage
FROM gradle:8.10.2-jdk21 AS builder
WORKDIR /app

# Copy source and build the executable jar.
COPY . .
RUN gradle clean bootJar --no-daemon

# Runtime stage
FROM eclipse-temurin:21-jre
WORKDIR /app

COPY --from=builder /app/build/libs/*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
