# Stage 1: Build Spring Boot App + React frontend
FROM maven:3.9.1-eclipse-temurin-17 AS build

WORKDIR /app

# Install Node.js and npm (required by frontend-maven-plugin)
RUN apt-get update && \
    apt-get install -y curl && \
    curl -fsSL https://deb.nodesource.com/setup_18.x | bash - && \
    apt-get install -y nodejs && \
    node -v && npm -v

# Copy all files
COPY . .

# Build the whole project (frontend + backend)
RUN mvn clean package -DskipTests

# Stage 2: Run only the Spring Boot JAR
FROM eclipse-temurin:17-jdk-alpine

WORKDIR /app

# Copy the jar file built in the previous stage
COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]