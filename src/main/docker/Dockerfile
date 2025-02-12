# Use an OpenJDK image as a base
FROM openjdk:21-jdk-slim

# Set working directory
WORKDIR /app

# Copy the built JAR into the container
COPY target/employee-0.0.1-SNAPSHOT.jar app.jar

# Expose the application port
EXPOSE 8081

# Run the application
CMD ["java", "-jar", "app.jar"]
