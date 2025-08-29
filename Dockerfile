FROM openjdk:17-jre-slim

# Set working directory
WORKDIR /app

# Copy the jar file
COPY target/ByteBazaar-0.0.1-SNAPSHOT.jar app.jar

# Expose port
EXPOSE 8080

# Add health check
HEALTHCHECK --interval=30s --timeout=10s --start-period=60s --retries=3 \
    CMD curl -f http://localhost:8080/actuator/health || exit 1

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
