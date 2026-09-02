# Use OpenJDK 17 Alpine (lightweight)
FROM eclipse-temurin:17-jdk-alpine

# Create app directory
WORKDIR /app

# Copy the Spring Boot jar into container
COPY target/springboot-crud-postgres-0.0.1-SNAPSHOT.jar app.jar

# Expose Spring Boot port
EXPOSE 8080

# Run the jar
ENTRYPOINT ["java","-jar","app.jar","--spring.profiles.active=aws"]
