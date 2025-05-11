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

# Convert postgres:// to jdbc:postgresql:// if needed
CMD if [ ! -z "$DATABASE_URL" ]; then \
      export SPRING_DATASOURCE_URL=$(echo $DATABASE_URL | sed 's/postgres:\/\//jdbc:postgresql:\/\//'); \
    fi && \
    java -jar app.jar

CMD ["java", "-jar", "app.jar"]


