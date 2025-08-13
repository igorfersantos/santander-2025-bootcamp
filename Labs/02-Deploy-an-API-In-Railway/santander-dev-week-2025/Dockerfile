# Use Eclipse Temurin 21 as base image
FROM eclipse-temurin:21-jdk-jammy as build

# Set working directory
WORKDIR /app

# Copy Gradle files
COPY build.gradle.kts settings.gradle.kts ./
COPY gradle ./gradle

# Make gradlew executable
RUN chmod +x ./gradlew

# Download dependencies
RUN ./gradlew dependencies --no-daemon

# Copy source code
COPY src ./src

# Build the application
RUN ./gradlew clean build -x test --no-daemon

# Production stage
FROM eclipse-temurin:21-jre-jammy

WORKDIR /app

# Copy the built JAR file
COPY --from=build /app/build/libs/santander-dev-week-2025-*.jar app.jar

# Expose the application port
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
