# Stage 1: Build the application
FROM maven:3.9-eclipse-temurin-21 AS build
WORKDIR /app

# Copy the pom.xml and download dependencies
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copy the source code and build the application
COPY src ./src
RUN mvn clean package -DskipTests

# Stage 2: Create the runtime image
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

# Copy the built jar from the build stage
COPY --from=build /app/target/spring-ai-with-local-llm-0.0.1-SNAPSHOT.jar app.jar

# Expose the application port
EXPOSE 8085

# Set the entrypoint
ENTRYPOINT ["java", "-jar", "app.jar"]

