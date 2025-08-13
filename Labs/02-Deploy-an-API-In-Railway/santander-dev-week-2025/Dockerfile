# Use Eclipse Temurin 21 as base image
FROM eclipse-temurin:21-jdk-jammy as build

# Set working directory
WORKDIR /app

# Copy Gradle wrapper and build files first (better caching)
COPY gradlew .
COPY gradle ./gradle
COPY build.gradle.kts settings.gradle.kts ./

# Make gradlew executable and download dependencies
RUN chmod +x gradlew && ./gradlew --version

# Copy source code
COPY src ./src

# Build the application and list the output directory
RUN ./gradlew clean bootJar -x test --no-daemon && \
    ls -la /app/build/libs/

# Production stage
FROM eclipse-temurin:21-jre-jammy

WORKDIR /app

# Copy the built JAR file
COPY --from=build /app/build/libs/app.jar .

# Set the active profile to 'prd' for production
ENV SPRING_PROFILES_ACTIVE=prd

# Verify the file was copied
RUN ls -la /app/

# Expose the application port
EXPOSE 8080

# Run the application with the prd profile
ENTRYPOINT ["java", "-Dspring.profiles.active=prd", "-jar", "app.jar"]