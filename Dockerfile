# Stage 1: Build frontend and backend
FROM maven:3.9.1-eclipse-temurin-17 AS build

WORKDIR /app

# Install Node.js manually (because base image doesn't have it)
RUN curl -fsSL https://deb.nodesource.com/setup_18.x | bash - && \
    apt-get update && \
    apt-get install -y nodejs

# Copy everything into the container
COPY . .

# Build frontend
WORKDIR /app/frontend
RUN npm install && npm run build

# Move built frontend into Spring Boot's static folder
RUN rm -rf /app/src/main/resources/static && \
    mkdir -p /app/src/main/resources/static && \
    cp -r build/* /app/src/main/resources/static/

# Build backend JAR
WORKDIR /app
RUN mvn clean package -DskipTests

# Stage 2: Run the built JAR
FROM eclipse-temurin:17-jdk-alpine

WORKDIR /app

COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]