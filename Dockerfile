# Build stage
FROM maven:3.9-eclipse-temurin-17 AS build
WORKDIR /app

# Copy only pom.xml first to cache dependencies
COPY pom.xml .
RUN mvn dependency:go-offline

# Copy source code and build
COPY src ./src
RUN mvn clean package -DskipTests

# Run stage
FROM eclipse-temurin:17-jre
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar

ENV SPRING_PROFILES_ACTIVE=prod

# Convert DATABASE_URL to JDBC format and run
CMD DATABASE_URL=$(echo $DATABASE_URL | sed 's|postgres://|jdbc:postgresql://|') && \
    java -jar \
    -Dspring.profiles.active=prod \
    -Dspring.datasource.url="$DATABASE_URL" \
    app.jar


