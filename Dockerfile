# Stage 1: Build frontend and backend using Maven (Node.js handled by frontend-maven-plugin)
FROM maven:3.9.1-eclipse-temurin-17 AS build

WORKDIR /app

# Copy all project files
COPY . .

# Run Maven package; assumes frontend plugin runs npm install and build
RUN mvn clean package -DskipTests

# Stage 2: Run the built Spring Boot JAR
FROM eclipse-temurin:17-jdk-alpine

WORKDIR /app

# Copy the packaged JAR from build stage
COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]