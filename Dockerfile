# Use a lightweight Java 17 runtime
FROM eclipse-temurin:17-jre-alpine

# Set working directory
WORKDIR /app

# Copy the JAR built by Maven
COPY target/awards-service-0.0.1-SNAPSHOT.jar app.jar

# Expose port (default for Spring Boot)
EXPOSE 8080

# Run the app
ENTRYPOINT ["java", "-jar", "app.jar"]
