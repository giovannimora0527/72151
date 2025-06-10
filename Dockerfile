# Use OpenJDK 21 as base image
FROM eclipse-temurin:21-jdk-jammy

# Set working directory
WORKDIR /app

# Copy the JAR file
COPY target/*.jar app.jar

# Command to run the application
ENTRYPOINT ["java","-jar","app.jar"]
