# Build stage
FROM maven:3.9-eclipse-temurin-21 AS build
WORKDIR /app

# Copy only pom.xml first to cache dependencies
COPY pom.xml .
RUN mvn dependency:go-offline

# Copy source code and build
COPY src ./src
RUN mvn clean package -DskipTests

# Run stage
FROM eclipse-temurin:21-jre
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8080

# Set non-sensitive environment variables with defaults
ENV SERVER_PORT=8080 \
    CORS_ALLOWED_ORIGINS=http://localhost:3000

ENTRYPOINT ["java", "-jar", "app.jar"] 